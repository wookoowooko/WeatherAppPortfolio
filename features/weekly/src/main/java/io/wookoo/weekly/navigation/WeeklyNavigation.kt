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
    val latitude: Double,
    val longitude: Double,
    val geoItemId: Long,
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
    latitude: Double,
    longitude: Double,
    geoItemId: Long
) {
    navigate(WeeklyRoute(latitude, longitude, geoItemId))
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
