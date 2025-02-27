package io.wookoo.main.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import io.wookoo.main.mvi.MainPageViewModel
import io.wookoo.main.screen.MainPageScreen
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

fun NavGraphBuilder.mainPage() {
    composable<MainRoute> {
        MainPageScreenRoot()
    }
}

@Composable
private fun MainPageScreenRoot(
    viewModel: MainPageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val onIntent = viewModel::onIntent
    MainPageScreen(
        state = state,
    )
}
