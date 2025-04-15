package io.wookoo.weatherappportfolio.appstate

import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import io.wookoo.designsystem.ui.theme.successColor
import io.wookoo.domain.service.IConnectivityObserver
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

@Composable
internal fun rememberAppState(
    networkMonitor: IConnectivityObserver,
    coroutineScope: CoroutineScope = rememberCoroutineScope(),
): AppState {
    val errorColor = MaterialTheme.colorScheme.error
    val successColor = successColor
    val context = LocalContext.current
    return remember(
        networkMonitor,
        coroutineScope,
        errorColor,
        successColor
    ) {
        AppState(
            coroutineScope = coroutineScope,
            networkMonitor = networkMonitor,
            context = context,
            errorColor = errorColor,
            successColor = successColor
        )
    }
}

@Stable
internal class AppState(
    coroutineScope: CoroutineScope,
    networkMonitor: IConnectivityObserver,
    private val errorColor: Color,
    private val successColor: Color,
    private val context: Context,
) {

    private val isOffline = networkMonitor.isOnline
        .map(Boolean::not)
        .stateIn(
            scope = coroutineScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = false,
        )

    private var firstLaunched = true

    private val _snackBarMessage = MutableStateFlow("")
    val snackBarMessage: StateFlow<String> = _snackBarMessage

    private val _snackBarColor = MutableStateFlow(errorColor)
    val snackBarColor: StateFlow<Color> = _snackBarColor

    private val _isSnackBarVisible = MutableStateFlow(false)
    val isSnackBarVisible: StateFlow<Boolean> = _isSnackBarVisible

    init {
        coroutineScope.launch {
            isOffline.collect { offline ->
                when {
                    offline -> {
                        _snackBarMessage.value =
                            context.getString(io.wookoo.androidresources.R.string.no_internet_connection)
                        _snackBarColor.value = errorColor
                        _isSnackBarVisible.value = true
                        firstLaunched = false
                    }

                    !firstLaunched -> {
                        _snackBarMessage.value =
                            context.getString(io.wookoo.androidresources.R.string.internet_connection_restore)
                        _snackBarColor.value = successColor
                        _isSnackBarVisible.value = true
                    }
                }
            }
        }
    }

    fun dismissSnackBar() {
        _isSnackBarVisible.value = false
    }

    fun showSnackBar(message: String, color: Color = errorColor) {
        _snackBarMessage.value = message
        _snackBarColor.value = color
        _isSnackBarVisible.value = true
    }
}
