package io.wookoo.cities.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.cities.mvi.OnGPSClick
import io.wookoo.cities.mvi.OnRequestGeoLocationPermission
import io.wookoo.cities.mvi.OnSearchQueryChange
import io.wookoo.cities.mvi.OnUpdateCurrentGeo
import io.wookoo.common.ext.hasLocationPermissions
import io.wookoo.designsystem.ui.components.SharedLocationItem
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.designsystem.ui.utils.SingleClickHandler.singleClick
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@Composable
@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
internal fun CitiesSearchBar(
    state: CitiesState,
    onIntent: (CitiesIntent) -> Unit,
    onHideBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val focusRequester = remember { FocusRequester() }
    var textFieldValue = remember(state.searchQuery) {
        TextFieldValue(
            text = state.searchQuery,
            selection = TextRange(state.searchQuery.length)
        )
    }

    SearchBar(
        modifier = modifier
            .focusRequester(focusRequester)
            .consumeWindowInsets(TopAppBarDefaults.windowInsets),
        expanded = true,
        onExpandedChange = {},
        inputField = {
            TextField(
                textStyle = MaterialTheme.typography.bodyLarge.copy(
                    color = if (state.isProcessing) {
                        MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                    } else {
                        MaterialTheme.colorScheme.onSurface
                    }
                ),
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                    disabledTextColor = Color.Transparent,
                    disabledLabelColor = Color.Transparent,
                    disabledContainerColor = Color.Transparent
                ),
                value = textFieldValue,
                onValueChange = { newValue ->
                    if (!state.isProcessing) {
                        textFieldValue = newValue
                        onIntent(OnSearchQueryChange(newValue.text))
                    }
                },
                enabled = !state.isProcessing,
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(
                        WindowInsets.displayCutout.only(
                            WindowInsetsSides.Horizontal
                        ).add(
                            WindowInsets.navigationBars.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                    ),
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    if (state.searchQuery.isEmpty()) {
                        Text(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            text = stringResource(io.wookoo.androidresources.R.string.search_your_location),
                            modifier = Modifier
                        )
                    }
                },
                trailingIcon = {
                    IconButton(
                        enabled = !state.isProcessing,
                        onClick = {
                            if (state.searchQuery.isNotEmpty()) {
                                onIntent(OnSearchQueryChange(""))
                            } else {
                                onHideBottomSheet()
                            }
                        }
                    ) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        if (state.isProcessing) {
            LinearProgressIndicator(
                modifier = Modifier.fillMaxWidth()
            )
        }
        TextButton(
            enabled = !state.isProcessing,
            modifier = Modifier
                .padding(top = small)
                .align(Alignment.CenterHorizontally),
            onClick = {
                if (context.hasLocationPermissions()) {
                    onIntent(OnGPSClick)
                } else {
                    onIntent(OnRequestGeoLocationPermission)
                }
            }
        ) {
            Icon(Icons.Default.NearMe, null, Modifier.padding(end = medium))
            SharedText(
                stringResource(io.wookoo.androidresources.R.string.find_me_button),
                style = MaterialTheme.typography.labelLarge
            )
        }

        state.gpsItem?.let { gpsItem ->
            SharedLocationItem(
                enabled = !state.isProcessing,
                isCurrent = true,
                modifier = Modifier
                    .padding(horizontal = large)
                    .padding(bottom = medium),
                countryName = gpsItem.countryName,
                cityName = gpsItem.cityName,
                urbanArea = gpsItem.urbanArea,
                onClick = {
                    singleClick {
                        onIntent(OnUpdateCurrentGeo(gpsItem.geoItemId))
                    }
                }

            )
        }
        HorizontalDivider()
        if (state.results.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                SharedText(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(io.wookoo.androidresources.R.string.no_results_found),
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                    ),
                    textAlign = TextAlign.Center
                )
            }
        } else {
            SearchResults(state, onIntent)
        }
    }

    LaunchedEffect(textFieldValue) {
        snapshotFlow { textFieldValue }
            .debounce(1000)
            .collect { latestValue ->
                val trimmed = latestValue.text.trim()
                if (trimmed != latestValue.text) {
                    val newValue = latestValue.copy(text = trimmed)
                    textFieldValue = newValue
                    onIntent(OnSearchQueryChange(newValue.text))
                } else {
                    onIntent(OnSearchQueryChange(latestValue.text))
                }
            }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
@Preview
private fun WelcomeSearchBarPreview() {
    WeatherAppPortfolioTheme {
        CitiesSearchBar(
            onIntent = {},
            state = CitiesState(),
            onHideBottomSheet = {}
        )
    }
}

@Composable
@Preview
private fun WelcomeSearchBarPreview2() {
    WeatherAppPortfolioTheme {
        CitiesSearchBar(
            onIntent = {},
            state = CitiesState(),
            onHideBottomSheet = {}
        )
    }
}
