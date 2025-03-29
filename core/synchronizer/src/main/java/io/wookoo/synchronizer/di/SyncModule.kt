package io.wookoo.synchronizer.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.domain.sync.ISyncManager
import io.wookoo.domain.sync.ISynchronizer
import io.wookoo.synchronizer.SyncManagerImpl
import io.wookoo.synchronizer.SynchronizerImpl

@Module
@InstallIn(SingletonComponent::class)
interface SyncModule {

    @Binds
    fun bindsSynchronizer(
        synchronizer: SynchronizerImpl,
    ): ISynchronizer

    @Binds
    fun bindsISyncManager(
        syncManager: SyncManagerImpl
    ): ISyncManager
}
