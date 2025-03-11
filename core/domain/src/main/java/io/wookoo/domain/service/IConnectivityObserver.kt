package io.wookoo.domain.service

import kotlinx.coroutines.flow.Flow

interface IConnectivityObserver {
    val isOnline: Flow<Boolean>
}