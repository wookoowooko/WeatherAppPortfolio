package io.wookoo.synchronizer

import io.wookoo.domain.annotations.ApplicationScope
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.sync.ISyncManager
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.shareIn
import javax.inject.Inject

/**
 * Implementation of [ISyncManager] responsible for synchronizing weather data when user settings change.
 *
 * This class observes changes in user settings and triggers synchronization when:
 * 1. User has selected locations (geo IDs list is not empty)
 * 2. Settings have actually changed (compared to last stored state)
 * 3. Changes are stable (debounced for 1 second)
 *
 * Uses [Channel] with CONFLATED capacity to ensure only the latest sync event is processed,
 * preventing multiple rapid syncs for quick consecutive changes.
 *
 * @property dataStore Repository for accessing and observing user settings
 * @property currentForecastRepo Repository for checking available forecast locations
 * @property scope Coroutine scope for managing the observation lifecycle
 */

class SyncManagerImpl @Inject constructor(
    private val dataStore: IDataStoreRepo,
    private val currentForecastRepo: ICurrentForecastRepo,
    @ApplicationScope private val scope: CoroutineScope,
) : ISyncManager {

    private var localSettings: io.wookoo.models.settings.UserSettingsModel? = null

    private val _syncChannel = Channel<Unit>(Channel.CONFLATED)
    override val syncChannel: Flow<Unit> = _syncChannel.receiveAsFlow()

    init {
        @OptIn(FlowPreview::class, ExperimentalCoroutinesApi::class)
        @Suppress("IgnoredReturnValue")
        currentForecastRepo
            .getCurrentForecastGeoItemIds()
            .map { it.isNotEmpty() }
            .filter { isValid -> isValid }
            .flatMapLatest {
                dataStore.userSettings
                    .onEach { settings ->
                        if (localSettings == null) localSettings = settings
                    }
                    .debounce(1000)
                    .map { settings ->
                        if (localSettings != settings) {
                            localSettings = settings
                            _syncChannel.trySend(Unit)
                        }
                    }
            }
            .shareIn(scope, SharingStarted.Eagerly, 0)
    }
}
