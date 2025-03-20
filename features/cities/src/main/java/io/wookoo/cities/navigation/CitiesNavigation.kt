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

fun NavGraphBuilder.citiesScreen(
    onBackIconClick: () -> Unit,
) {
    composable<CitiesRoute> {
        CitiesScreenRoot(
            onBackIconClick = onBackIconClick
        )
    }
}

fun NavHostController.navigateToCities() {
    navigate(CitiesRoute)
}

@Composable
private fun CitiesScreenRoot(
    viewModel: CitiesViewModel = hiltViewModel(),
    onBackIconClick: () -> Unit,
) {
    val state by viewModel.state.collectAsStateWithLifecycle()

    CitiesScreen(
        onBackIconClick = onBackIconClick,
        state = state,
        onIntent = viewModel::onIntent
    )
}
