package io.wookoo.common.mvi

interface StoreDispatcher<Intent> {
    fun dispatch(intent: Intent)
}
