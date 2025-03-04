package io.wookoo.weekly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.wookoo.weekly.screen.WeeklyPageScreen
import kotlinx.serialization.Serializable

@Serializable
data object WeeklyRoute

fun NavGraphBuilder.weeklyPage(
    onBackIconClick: () -> Unit,
) {
    composable<WeeklyRoute> {
        WeeklyPageScreenRoot(
            onBackIconClick = onBackIconClick
        )
    }
}

fun NavHostController.navigateToWeeklyPage() {
    navigate(WeeklyRoute)
}

@Composable
private fun WeeklyPageScreenRoot(
    onBackIconClick: () -> Unit,
) {
    WeeklyPageScreen(
        onBackIconClick = onBackIconClick
    )
}
