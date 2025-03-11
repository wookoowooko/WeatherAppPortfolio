package io.wookoo.connectivity.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.connectivity.observer.AndroidConnectivityObserverImpl
import io.wookoo.domain.service.IConnectivityObserver

@Module
@InstallIn(SingletonComponent::class)
interface ConnectivityModule {

    @Binds
    fun bindsIConnectivityObserver(
        impl: AndroidConnectivityObserverImpl,
    ): IConnectivityObserver
}
