package io.wookoo.weekly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.wookoo.weekly.screen.WeeklyPageScreen
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyRoute(
    val geoItemId: Long
)

fun NavGraphBuilder.weeklyPage(
    onBackIconClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    composable<WeeklyRoute> { navBackStackEntry: NavBackStackEntry ->
        WeeklyPageScreenRoot(
            onBackIconClick = onBackIconClick,
            onShowSnackBar = onShowSnackBar,
            navBackStackEntry = navBackStackEntry,
        )
    }
}

fun NavHostController.navigateToWeeklyPage(
    geoItemId: Long,

) {
    navigate(WeeklyRoute(geoItemId))
}

@Composable
private fun WeeklyPageScreenRoot(
    onBackIconClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    navBackStackEntry: NavBackStackEntry,
) {
    WeeklyPageScreen(
        onBackIconClick = onBackIconClick,
        onShowSnackBar = onShowSnackBar,
        navBackStackEntry = navBackStackEntry,
    )
}
