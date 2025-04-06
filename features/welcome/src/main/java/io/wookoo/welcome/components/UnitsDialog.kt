package io.wookoo.welcome.components


import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import io.wookoo.androidresources.R
import io.wookoo.common.ext.asUnitString
import io.wookoo.designsystem.ui.components.SharedRadioGroup
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.welcome.mvi.OnChangeUnitsDialogState
import io.wookoo.welcome.mvi.SaveSelectedPrecipitation
import io.wookoo.welcome.mvi.SaveSelectedTemperature
import io.wookoo.welcome.mvi.SaveSelectedWindSpeed
import io.wookoo.welcome.mvi.WelcomePageIntent
import io.wookoo.welcome.mvi.WelcomePageState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun UnitsDialog(
    modifier: Modifier = Modifier,
    state: WelcomePageState,
    onIntent: (WelcomePageIntent) -> Unit,
) {
    BasicAlertDialog(
        properties = DialogProperties(),
        onDismissRequest = {
            onIntent(OnChangeUnitsDialogState(false))
        },
        modifier = modifier.fillMaxWidth()
    ) {
        Card {
            SharedText(
                weight = FontWeight.Bold,
                style = MaterialTheme.typography.titleMedium,
                text = stringResource(R.string.units_of_measurement),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(medium), textAlign = TextAlign.Center
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

@Composable
@Preview(showBackground = true)
private fun PreviewUnits() {
    WeatherAppPortfolioTheme {
        UnitsDialog(
            state = WelcomePageState(),
            onIntent = {}
        )
    }
}
