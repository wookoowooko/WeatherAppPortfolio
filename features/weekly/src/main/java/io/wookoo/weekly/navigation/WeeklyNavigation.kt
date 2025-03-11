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
    onShowSnackBar: (String) -> Unit,
) {
    composable<WeeklyRoute> {
        WeeklyPageScreenRoot(
            onBackIconClick = onBackIconClick,
            onShowSnackBar = onShowSnackBar
        )
    }
}

fun NavHostController.navigateToWeeklyPage() {
    navigate(WeeklyRoute)
}

@Composable
private fun WeeklyPageScreenRoot(
    onBackIconClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    WeeklyPageScreen(
        onBackIconClick = onBackIconClick,
        onShowSnackBar = onShowSnackBar
    )
}
