package io.wookoo.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.wookoo.main.mvi.MainPageContract
import io.wookoo.main.mvi.MainPageViewModel
import io.wookoo.main.screen.MainPageScreen
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

fun NavGraphBuilder.mainPage(
    onRequestLocationPermissions: () -> Unit,
    onNavigate: () -> Unit,
) {
    composable<MainRoute> {
        MainPageScreenRoot(
            onRequestLocationPermissions = onRequestLocationPermissions,
            onNavigate = onNavigate
        )
    }
}

@Composable
private fun MainPageScreenRoot(
    viewModel: MainPageViewModel = hiltViewModel(),
    onRequestLocationPermissions: () -> Unit,
    onNavigate: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    MainPageScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                MainPageContract.OnIntent.OnRequestGeoLocationPermission -> onRequestLocationPermissions()
                MainPageContract.OnIntent.OnNavigateToWeekly -> onNavigate()
                else -> Unit
            }
            viewModel.onIntent(intent)
        }
    )
}
