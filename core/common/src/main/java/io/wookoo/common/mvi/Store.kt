package io.wookoo.common.mvi

import io.wookoo.domain.annotations.StoreViewModelScope
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@Suppress("UnnecessaryAbstractClass")
abstract class Store<State, Intent, Effect>(
    @StoreViewModelScope private val storeScope: CoroutineScope,
    initialState: State,
    private val reducer: Reducer<State, Intent>,
) : StoreDispatcher<Intent> {
    private val _state = MutableStateFlow(initialState)

    protected val state: StateFlow<State> = _state.onStart {
        initializeObservers()
    }.stateIn(
        storeScope,
        SharingStarted.WhileSubscribed(5000L),
        initialState
    )

    private val _sideEffects = MutableSharedFlow<Effect>(replay = 0)
    private val sideEffects: SharedFlow<Effect> = _sideEffects.asSharedFlow()

    private fun updateState(intent: Intent) {
        _state.update { currentState ->
            val newState = reducer.reduce(currentState, intent)
            newState
        }
    }

    protected fun emitSideEffect(effect: Effect) {
        storeScope.launch {
            _sideEffects.emit(effect)
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

    fun createSideEffect(): SharedFlow<Effect> {
        return sideEffects
    }
}
