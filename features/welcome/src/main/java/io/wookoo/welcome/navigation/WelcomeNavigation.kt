package io.wookoo.welcome.navigation

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
import io.wookoo.welcome.mvi.SideEffect
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
                    is SideEffect.ShowSnackBar -> onShowSnackBar(sideEffect.message.asLocalizedString(context))
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
