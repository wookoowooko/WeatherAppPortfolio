package io.wookoo.main.navigation

import android.content.Intent
import android.provider.Settings
import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.main.mvi.MainPageEffect
import io.wookoo.main.mvi.MainPageViewModel
import io.wookoo.main.mvi.OnNavigateToCities
import io.wookoo.main.mvi.OnNavigateToWeekly
import io.wookoo.main.mvi.OnRequestGeoLocationPermission
import io.wookoo.main.screen.MainPageScreen
import kotlinx.serialization.Serializable

@Serializable
data object MainRoute

fun NavGraphBuilder.mainPage(
    onRequestLocationPermissions: () -> Unit,
    onNavigateToWeekly: (geoItemId: Long) -> Unit,
    onNavigateToCities: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    composable<MainRoute> {
        MainPageScreenRoot(
            onRequestLocationPermissions = onRequestLocationPermissions,
            onNavigateToWeekly = onNavigateToWeekly,
            onNavigateToCities = onNavigateToCities,
            onShowSnackBar = onShowSnackBar
        )
    }
}

@Composable
private fun MainPageScreenRoot(
    viewModel: MainPageViewModel = hiltViewModel(),
    onRequestLocationPermissions: () -> Unit,
    onNavigateToWeekly: (geoItemId: Long) -> Unit,
    onNavigateToCities: () -> Unit,
    onShowSnackBar: (String) -> Unit,
) {
    val owner = LocalLifecycleOwner.current
    val state by viewModel.state.collectAsStateWithLifecycle()
    val context = LocalContext.current

    LaunchedEffect(Unit) {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is MainPageEffect.OnShowSnackBar -> onShowSnackBar(
                        sideEffect.message.asLocalizedString(
                            context
                        )
                    )

                    is MainPageEffect.OnShowSettingsDialog -> {
                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
                        }
                        context.startActivity(intent)
                    }
                }
            }
        }
    }


    MainPageScreen(
        state = state,
        onIntent = { intent ->
            when (intent) {
                OnRequestGeoLocationPermission -> onRequestLocationPermissions()
                is OnNavigateToWeekly -> onNavigateToWeekly(
                    intent.geoItemId,
                )

                is OnNavigateToCities -> onNavigateToCities()
                else -> Unit
            }
            viewModel.onIntent(intent)
        }
    )
}
