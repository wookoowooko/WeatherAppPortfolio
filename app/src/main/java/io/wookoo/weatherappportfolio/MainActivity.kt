package io.wookoo.weatherappportfolio

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import dagger.hilt.android.AndroidEntryPoint
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.main.navigation.MainRoute
import io.wookoo.main.navigation.mainPage

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherAppPortfolioTheme {
                NavHost(
                    navController = rememberNavController(),
                    startDestination = MainRoute
                ) {
                    mainPage()
                }
            }
        }
    }
}
