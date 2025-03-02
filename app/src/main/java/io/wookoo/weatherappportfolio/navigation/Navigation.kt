package io.wookoo.weatherappportfolio.navigation

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import io.wookoo.domain.settings.UserSettingsModel
import io.wookoo.main.navigation.MainRoute
import io.wookoo.main.navigation.mainPage
import io.wookoo.welcome.navigation.WelcomeRoute
import io.wookoo.welcome.navigation.welcomePage
import kotlinx.serialization.Serializable

@Serializable
private data object WelcomeGraph

@Serializable
private data object MainGraph

@Composable
internal fun Navigation(
    onRequestLocationPermission: () -> Unit,
    userSettings: UserSettingsModel?,
) {
    NavHost(
        navController = rememberNavController(),
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
                onRequestLocationPermission = onRequestLocationPermission
            )
        }

        navigation<MainGraph>(
            startDestination = MainRoute,
        ) {
            mainPage(
                onRequestLocationPermissions = onRequestLocationPermission
            )
        }
    }
}
