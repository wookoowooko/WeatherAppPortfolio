package io.wookoo.cities.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.wookoo.cities.mvi.CitiesViewModel
import io.wookoo.cities.screen.CitiesScreen
import kotlinx.serialization.Serializable

@Serializable
data object CitiesRoute

fun NavGraphBuilder.citiesScreen() {
    composable<CitiesRoute> {
        CitiesScreenRoot()
    }
}

fun NavHostController.navigateToCities() {
    navigate(CitiesRoute)
}

@Composable
private fun CitiesScreenRoot(
    viewModel: CitiesViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CitiesScreen(
        state = state,
    )
}
