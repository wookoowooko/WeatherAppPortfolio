package io.wookoo.welcome.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.wookoo.welcome.mvi.WelcomePageContract
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
    val state by viewModel.state.collectAsStateWithLifecycle()

    WelcomePageScreen(
        state = state,
        onIntent = { onIntent ->
            when (onIntent) {
                is WelcomePageContract.OnIntent.OnRequestGeoLocationPermission -> onRequestLocationPermission()
                else -> Unit
            }
            viewModel.onIntent(onIntent)
        }
    )
}
