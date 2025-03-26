package io.wookoo.weatherappportfolio.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.wookoo.cities.navigation.citiesScreen
import io.wookoo.cities.navigation.navigateToCities
import io.wookoo.main.navigation.MainRoute
import io.wookoo.main.navigation.mainPage
import io.wookoo.weekly.navigation.navigateToWeeklyPage
import io.wookoo.weekly.navigation.weeklyPage
import io.wookoo.welcome.navigation.WelcomeRoute
import io.wookoo.welcome.navigation.welcomePage
import kotlinx.serialization.Serializable

@Serializable
internal data object WelcomeGraph

@Serializable
internal data object MainGraph

@Composable
internal fun Navigation(
    onRequestLocationPermission: () -> Unit,
    onShowSnackBar: (String) -> Unit,
    onSyncRequest: (Long, Boolean) -> Unit,
    startDestination: Any,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = startDestination,
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        },
        exitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        },
    ) {
        navigation<WelcomeGraph>(
            startDestination = WelcomeRoute,

            ) {
            welcomePage(
                onRequestLocationPermission = onRequestLocationPermission,
                onShowSnackBar = onShowSnackBar,
                onSyncRequest = onSyncRequest,
            )
        }

        navigation<MainGraph>(
            startDestination = MainRoute,
        ) {
            mainPage(
                onRequestLocationPermissions = onRequestLocationPermission,
                onNavigateToWeekly = { geoItemId ->
                    navController.navigateToWeeklyPage(geoItemId)
                },
                onNavigateToCities = {
                    navController.navigateToCities()
                },
                onShowSnackBar = onShowSnackBar
            )
            weeklyPage(
                onBackIconClick = {
                    if (navController.canGoBack) navController.popBackStack()
                },
                onShowSnackBar = onShowSnackBar
            )

            citiesScreen(
                onBackIconClick = {
                    if (navController.canGoBack) navController.popBackStack()
                },
                onRequestLocationPermission = onRequestLocationPermission,
                onShowSnackBar = onShowSnackBar,
                onSyncRequest = onSyncRequest
            )
        }

    }
}

private val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
