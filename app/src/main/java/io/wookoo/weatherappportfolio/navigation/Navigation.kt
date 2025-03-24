package io.wookoo.weatherappportfolio.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.lifecycle.Lifecycle
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.wookoo.cities.navigation.citiesScreen
import io.wookoo.cities.navigation.navigateToCities
import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.main.navigation.MainRoute
import io.wookoo.main.navigation.mainPage
import io.wookoo.weekly.navigation.navigateToWeeklyPage
import io.wookoo.weekly.navigation.weeklyPage
import io.wookoo.welcome.navigation.WelcomeRoute
import io.wookoo.welcome.navigation.welcomePage
import kotlinx.serialization.Serializable

@Serializable
private data object WelcomeGraph

@Serializable
private data object MainGraph

@Composable
internal fun Navigation(
    userSettings: UserSettingsModel?,
    onRequestLocationPermission: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = if (userSettings?.isLocationChoose == true) {
            MainGraph
        } else {
            WelcomeGraph
        },
        popEnterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> -fullWidth },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        },
        popExitTransition = {
            slideOutHorizontally(
                targetOffsetX = { fullWidth -> fullWidth },
                animationSpec = tween(
                    durationMillis = 300,
                    easing = FastOutSlowInEasing
                )
            )
        },
        enterTransition = {
            slideInHorizontally(
                initialOffsetX = { fullWidth -> fullWidth },
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
                onShowSnackBar = onShowSnackBar
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
                onShowSnackBar = onShowSnackBar
            )
        }
    }
}

private val NavHostController.canGoBack: Boolean
    get() = this.currentBackStackEntry?.lifecycle?.currentState == Lifecycle.State.RESUMED
