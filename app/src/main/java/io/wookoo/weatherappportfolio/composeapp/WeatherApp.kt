package io.wookoo.weatherappportfolio.composeapp

import androidx.compose.animation.Crossfade
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.wookoo.designsystem.ui.components.SharedCustomSnackBar
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.weatherappportfolio.appstate.AppState
import io.wookoo.weatherappportfolio.navigation.Navigation

@Composable
internal fun WeatherApp(
    appState: AppState,
    onRequestLocationPermission: () -> Unit,
) {
    val userSettings by appState.settings.collectAsState(initial = null)
    val isOffline by appState.isOffline.collectAsStateWithLifecycle()
    var snackBarMessage by rememberSaveable { mutableStateOf("") }
    var isSnackBarVisible by rememberSaveable { mutableStateOf(false) }
    val notConnectedMessage = stringResource(io.wookoo.androidresources.R.string.error_no_internet)
    var firstLaunched by rememberSaveable { mutableStateOf(true) }
    var snackBarColor by remember{ mutableStateOf(Color.Red) }

    LaunchedEffect(isOffline) {
        if (isOffline) {
            snackBarMessage = notConnectedMessage
            isSnackBarVisible = true
            snackBarColor = Color.Red
            firstLaunched = false
        } else {
            if (!firstLaunched) {
                snackBarMessage = "Internet restored"
                isSnackBarVisible = true
                snackBarColor = Color.Green
            }

        }
    }

    Crossfade(
        targetState = when {
            userSettings?.isLocationChoose == null -> io.wookoo.designsystem.ui.Crossfade.LOADING
            else -> io.wookoo.designsystem.ui.Crossfade.CONTENT
        },
        label = ""
    ) { screenState ->
        when (screenState) {
            io.wookoo.designsystem.ui.Crossfade.LOADING -> SharedLottieLoader()
            io.wookoo.designsystem.ui.Crossfade.CONTENT -> {
                Navigation(
                    onRequestLocationPermission = onRequestLocationPermission,
                    userSettings = userSettings,
                    onShowSnackBar = { message ->
                        snackBarMessage = message
                        isSnackBarVisible = true
                    }
                )
                SharedCustomSnackBar(
                    snackBarColor = snackBarColor,
                    message = snackBarMessage,
                    isVisible = isSnackBarVisible,
                    onDismiss = { isSnackBarVisible = false }
                )
            }

            else -> Unit
        }
    }
}
