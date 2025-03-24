package io.wookoo.cities.navigation

import android.content.Intent
import android.provider.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import io.wookoo.cities.mvi.CitiesSideEffect
import io.wookoo.cities.mvi.CitiesViewModel
import io.wookoo.cities.mvi.OnRequestGeoLocationPermission
import io.wookoo.cities.screen.CitiesScreen
import io.wookoo.common.ext.asLocalizedString
import kotlinx.serialization.Serializable

@Serializable
data object CitiesRoute

fun NavGraphBuilder.citiesScreen(
    onBackIconClick: () -> Unit,
    onRequestLocationPermission: () -> Unit,
    onShowSnackBar: (String) -> Unit
    ) {
    composable<CitiesRoute> {
        CitiesScreenRoot(
            onBackIconClick = onBackIconClick,
            onRequestLocationPermission = onRequestLocationPermission,
            onShowSnackBar = onShowSnackBar
        )
    }
}

fun NavHostController.navigateToCities() {
    navigate(CitiesRoute)
}

@Composable
private fun CitiesScreenRoot(
    viewModel: CitiesViewModel = hiltViewModel(),
    onBackIconClick: () -> Unit,
    onRequestLocationPermission: () -> Unit,
    onShowSnackBar: (String) -> Unit
    ) {
    val owner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val state by viewModel.state.collectAsStateWithLifecycle()


    LaunchedEffect(Unit) {
        owner.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.sideEffect.collect { sideEffect ->
                when (sideEffect) {
                    is CitiesSideEffect.ShowSnackBar -> onShowSnackBar(
                        sideEffect.message.asLocalizedString(context)
                    )

//                    is CitiesSideEffect.OnShowSettingsDialog -> {
//                        val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS).apply {
//                            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_SINGLE_TOP
//                        }
//                        context.startActivity(intent)
//                    }
                    else -> Unit
                }
            }
        }
    }


    CitiesScreen(
        onBackIconClick = onBackIconClick,
        state = state,
        onIntent = { onIntent ->
            when (onIntent) {
                is OnRequestGeoLocationPermission -> onRequestLocationPermission()
                else -> Unit
            }
            viewModel.onIntent(onIntent)
        }
    )
}
