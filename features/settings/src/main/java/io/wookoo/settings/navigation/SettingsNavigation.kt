package io.wookoo.settings.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.wookoo.settings.mvi.SettingsPageViewModel
import io.wookoo.settings.screen.SettingsPageScreen
import kotlinx.serialization.Serializable

@Serializable
data object SettingsRoute

fun NavGraphBuilder.settingsPage(
    onBackIconClick: () -> Unit
) {
    composable<SettingsRoute> {
        SettingsPageScreenRoot(
            onBackIconClick = onBackIconClick
        )
    }
}

fun NavHostController.navigateToSettingsPage() {
    navigate(SettingsRoute)
}

@Composable
private fun SettingsPageScreenRoot(
    onBackIconClick: () -> Unit,
    viewModel: SettingsPageViewModel = hiltViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    SettingsPageScreen(
        onBackIconClick = onBackIconClick,
        state = state,
        onIntent = viewModel::onIntent
    )
}
