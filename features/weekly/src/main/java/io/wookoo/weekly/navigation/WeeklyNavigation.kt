package io.wookoo.weekly.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.toRoute
import io.wookoo.weekly.screen.WeeklyPageScreen
import kotlinx.serialization.Serializable

@Serializable
data class WeeklyRoute(
    val cityName: String,
)

fun NavGraphBuilder.weeklyPage(
    onBackIconClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    composable<WeeklyRoute> { navBackStackEntry ->
        val args = navBackStackEntry.toRoute<WeeklyRoute>()
        WeeklyPageScreenRoot(
            onBackIconClick = onBackIconClick,
            onShowSnackBar = onShowSnackBar,
            cityName = args.cityName,
        )
    }
}

fun NavHostController.navigateToWeeklyPage(
    cityName: String,
) {
    navigate(WeeklyRoute(cityName))
}

@Composable
private fun WeeklyPageScreenRoot(
    onBackIconClick: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    cityName: String,
) {
    WeeklyPageScreen(
        onBackIconClick = onBackIconClick,
        onShowSnackBar = onShowSnackBar,
        cityName = cityName,
    )
}
