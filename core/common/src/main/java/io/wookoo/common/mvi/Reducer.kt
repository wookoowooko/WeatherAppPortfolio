package io.wookoo.common.mvi

interface Reducer<State, Intent> {
    fun reduce(state: State, intent: Intent): State
}
