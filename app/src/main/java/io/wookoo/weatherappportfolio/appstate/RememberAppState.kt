package io.wookoo.weatherappportfolio.appstate

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.service.IConnectivityObserver
import io.wookoo.domain.settings.UserSettingsModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn

@Composable
fun rememberAppState(
    networkMonitor: IConnectivityObserver,
    dataStore: IDataStoreRepo,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    return remember(
        networkMonitor,
        coroutineScope,
        dataStore
    ) {
        AppState(
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
            dataStore = dataStore,
        )
    }
}

@Stable
class AppState(
    coroutineScope: CoroutineScope,
    networkMonitor: IConnectivityObserver,
    dataStore: IDataStoreRepo,
) {

    val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = false,
        )

    val settings = dataStore.userSettings
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = UserSettingsModel(),
        )
}
