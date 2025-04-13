package io.wookoo.settings.screen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.androidresources.R
import io.wookoo.common.ext.asUnitString
import io.wookoo.designsystem.ui.adaptive.Device
import io.wookoo.designsystem.ui.adaptive.Orientation
import io.wookoo.designsystem.ui.adaptive.rememberPane
import io.wookoo.designsystem.ui.components.SharedRadioGroup
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.settings.mvi.SaveSelectedPrecipitation
import io.wookoo.settings.mvi.SaveSelectedTemperature
import io.wookoo.settings.mvi.SaveSelectedWindSpeed
import io.wookoo.settings.mvi.SettingsIntent
import io.wookoo.settings.mvi.SettingsState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun SettingsPageScreen(
    onBackIconClick: () -> Unit,
    onIntent: (SettingsIntent) -> Unit,
    state: SettingsState,
) {
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets.add(
                    WindowInsets.displayCutout.only(
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
                    SharedText(stringResource(io.wookoo.androidresources.R.string.settings))
                }
            )
        }
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
                .padding(it)
                .windowInsetsPadding(
                    WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                )
        ) {
            val cardModifier = when (val pane = rememberPane()) {
                is Device.Smartphone -> Modifier.fillMaxWidth()
                is Device.Tablet -> when (pane.orientation) {
                    Orientation.Portrait -> Modifier.fillMaxWidth(0.5f)
                    Orientation.Landscape -> Modifier.fillMaxWidth(0.5f)
                }
            }

            Card(
                modifier = Modifier.padding(large)
                    then (cardModifier)
            ) {
                SharedText(
                    weight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(R.string.units_of_measurement),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(medium),
                    textAlign = TextAlign.Center
                )

                SharedText(
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(R.string.wind),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = medium)
                )
                SharedRadioGroup(
                    modifier = Modifier.padding(small),
                    radioOptions = state.windSpeedUnitOptions.map { options ->
                        stringResource(options.stringValue.asUnitString())
                    },
                    selectedOption = stringResource(state.selectedWindSpeedUnit.stringValue.asUnitString()),
                    onOptionSelect = { index ->
                        onIntent(SaveSelectedWindSpeed(state.windSpeedUnitOptions[index].apiValue))
                    }
                )
                SharedText(
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(R.string.precipitation),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = medium)
                )
                SharedRadioGroup(
                    modifier = Modifier.padding(small),
                    radioOptions = state.precipitationUnitOptions.map { options ->
                        stringResource(options.stringValue.asUnitString())
                    },
                    selectedOption = stringResource(
                        state.selectedPrecipitationUnit.stringValue.asUnitString()
                    ),
                    onOptionSelect = { index ->
                        onIntent(
                            SaveSelectedPrecipitation(state.precipitationUnitOptions[index].apiValue)
                        )
                    }
                )
                SharedText(
                    style = MaterialTheme.typography.titleMedium,
                    text = stringResource(R.string.temperature),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = medium)
                )

                SharedRadioGroup(
                    modifier = Modifier.padding(small),
                    radioOptions = state.temperatureUnitOptions.map { options ->
                        stringResource(options.stringValue.asUnitString())
                    },
                    selectedOption = stringResource(
                        state.selectedTemperatureUnit.stringValue.asUnitString()
                    ),
                    onOptionSelect = { index ->
                        onIntent(SaveSelectedTemperature(state.temperatureUnitOptions[index].apiValue))
                    }
                )
            }
        }
    }
}

@Composable
@Preview
private fun SettingsPageScreenPreview() {
    WeatherAppPortfolioTheme {
        SettingsPageScreen(
            onBackIconClick = {},
            state = SettingsState(),
            onIntent = {}
        )
    }
}
