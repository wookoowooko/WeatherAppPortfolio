package io.wookoo.common.mvi

import io.wookoo.domain.annotations.StoreViewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

open class Store<State, Intent, Effect : Any?>(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    initialState: State,
    private val reducer: Reducer<State, Intent>,
    private val defaultEffect: Effect? = null,
) : StoreDispatcher<Intent> {
    private val _state = MutableStateFlow(initialState)

    protected val state: StateFlow<State> = _state.onStart {
        initializeObservers()
    }.stateIn(
        storeScope,
        SharingStarted.WhileSubscribed(5000L),
        initialState
    )

    private val _sideEffects = MutableSharedFlow<Effect?>(replay = 0)

    protected fun emitSideEffect(effect: Effect? = defaultEffect) {
        if (effect != null) {
            storeScope.launch {
                _sideEffects.emit(effect)
            }
        }
    }

    private fun updateState(intent: Intent) {
        _state.update { currentState ->
            val newState = reducer.reduce(currentState, intent)
            newState
        }
    }

    override fun dispatch(intent: Intent) {
        updateState(intent)
        handleSideEffects(intent)
    }

    protected open fun handleSideEffects(intent: Intent) {}
    protected open fun initializeObservers() {}
    open fun clear() {}

    fun createState(): StateFlow<State> {
        return state
    }

    fun createSideEffect(): SharedFlow<Effect?> {
        return _sideEffects.asSharedFlow()
    }
}

open class SimpleStore<State, Intent>(
    storeScope: CoroutineScope,
    initialState: State,
    reducer: Reducer<State, Intent>,
) : Store<State, Intent, Nothing?>(
    storeScope = storeScope,
    initialState = initialState,
    reducer = reducer,
    defaultEffect = null
)
