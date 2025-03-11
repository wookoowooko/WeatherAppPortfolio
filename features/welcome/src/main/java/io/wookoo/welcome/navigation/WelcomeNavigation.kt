package io.wookoo.welcome.navigation

import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.welcome.mvi.OnRequestGeoLocationPermission
import io.wookoo.welcome.mvi.WelcomeSideEffect
import io.wookoo.welcome.mvi.WelcomePageViewModel
import io.wookoo.welcome.screen.WelcomePageScreen
import kotlinx.serialization.Serializable

@Serializable
data object WelcomeRoute

fun NavGraphBuilder.welcomePage(
    onRequestLocationPermission: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    composable<WelcomeRoute> {
        WelcomePageScreenRoot(
            onRequestLocationPermission = onRequestLocationPermission,
            onShowSnackBar = onShowSnackBar,
        )
    }
}

@Composable
private fun WelcomePageScreenRoot(
    viewModel: WelcomePageViewModel = hiltViewModel(),
    onRequestLocationPermission: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val owner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is WelcomeSideEffect.ShowSnackBar -> onShowSnackBar(
                        sideEffect.message.asLocalizedString(
                            context
                        )
                    )

                    is WelcomeSideEffect.OnShowSettingsDialog -> {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }

    WelcomePageScreen(
        state = state,
        onIntent = { onIntent ->
            when (onIntent) {
                is OnRequestGeoLocationPermission -> onRequestLocationPermission()
                else -> Unit
            }
            viewModel.onIntent(onIntent)
        }
    )
}
