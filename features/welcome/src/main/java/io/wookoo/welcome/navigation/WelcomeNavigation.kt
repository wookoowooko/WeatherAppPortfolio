package io.wookoo.welcome.navigation

import android.widget.Toast
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.wookoo.common.collectWithLifecycle
import io.wookoo.welcome.mvi.OnRequestGeoLocationPermission
import io.wookoo.welcome.mvi.SideEffect
import io.wookoo.welcome.mvi.WelcomePageViewModel
import io.wookoo.welcome.screen.WelcomePageScreen
import kotlinx.serialization.Serializable

@Serializable
data object WelcomeRoute

fun NavGraphBuilder.welcomePage(
    onRequestLocationPermission: () -> Unit,
) {
    composable<WelcomeRoute> {
        WelcomePageScreenRoot(
            onRequestLocationPermission = onRequestLocationPermission
        )
    }
}

@Composable
private fun WelcomePageScreenRoot(
    viewModel: WelcomePageViewModel = hiltViewModel(),
    onRequestLocationPermission: () -> Unit,
) {
    val context = LocalContext.current
    val owner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()

    viewModel.sideEffect.collectWithLifecycle(owner) {
        when (it) {
            is SideEffect.ShowSnackBar -> Toast.makeText(context, "Error", Toast.LENGTH_SHORT)
                .show()
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
