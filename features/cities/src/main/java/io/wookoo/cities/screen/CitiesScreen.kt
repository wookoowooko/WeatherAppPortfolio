package io.wookoo.cities.screen

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
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.components.CitiesFromDB
import io.wookoo.cities.components.CitiesSearchBar
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.cities.mvi.OnChangeBottomSheetVisibility
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
        CitiesFromDB(state, modifier = Modifier.padding(it))

        if (state.bottomSheetExpanded) {
            ModalBottomSheet(
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

@Composable
@Preview
private fun CitiesScreenPreview() {
    WeatherAppPortfolioTheme {
        CitiesScreen(
            state = CitiesState(),
            onIntent = {},
            onBackIconClick = {}
        )
    }
}
