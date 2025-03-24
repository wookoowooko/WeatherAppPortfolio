package io.wookoo.cities.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.components.CitiesFromDB
import io.wookoo.cities.components.CitiesSearchBar
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.cities.mvi.OnChangeBottomSheetVisibility
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CitiesScreen(
    state: CitiesState,
    onIntent: (CitiesIntent) -> Unit,
    onBackIconClick: () -> Unit,
) {
    BackHandler(enabled = !state.isProcessing) { }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets.add(
                    androidx.compose.foundation.layout.WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                ),
                navigationIcon = {
                    IconButton(
                        enabled = !state.isProcessing,
                        onClick = onBackIconClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    SharedText(stringResource(io.wookoo.androidresources.R.string.locations))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                onIntent(OnChangeBottomSheetVisibility(true))
            }) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Crossfade(
            targetState = when {
                state.isLoading -> io.wookoo.designsystem.ui.Crossfade.LOADING
                else -> io.wookoo.designsystem.ui.Crossfade.CONTENT
            },
            label = ""
        ) { screenState ->
            when (screenState) {
                io.wookoo.designsystem.ui.Crossfade.LOADING -> SharedLottieLoader()
                io.wookoo.designsystem.ui.Crossfade.CONTENT -> {
                    CitiesFromDB(state, modifier = Modifier.padding(it), onIntent = onIntent)
                    if (state.bottomSheetExpanded) {
                        ModalBottomSheet(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            modifier = Modifier.fillMaxSize(),
                            onDismissRequest = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onIntent(OnChangeBottomSheetVisibility(false))
                                    }
                                }
                            },
                            sheetState = sheetState
                        ) {
                            CitiesSearchBar(
                                onIntent = onIntent,
                                state = state,
                                onHideBottomSheet = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            onIntent(OnChangeBottomSheetVisibility(false))
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun CitiesScreenPreview() {
    WeatherAppPortfolioTheme {
        CitiesScreen(
            state = CitiesState(
                isLoading = false
            ),
            onIntent = {},
            onBackIconClick = {}
        )
    }
}
