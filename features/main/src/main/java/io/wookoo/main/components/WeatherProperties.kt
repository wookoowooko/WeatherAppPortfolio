package io.wookoo.main.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.designsystem.ui.components.SharedWeatherItem
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.main.mvi.MainPageState
import io.wookoo.models.ui.UiCurrentWeatherModel
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.units.WindDirection

@Composable
fun WeatherProperties(
    state: MainPageState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_humidity,
            text = state.currentWeather.humidity.value.asLocalizedUnitValueString(
                state.currentWeather.humidity.unit,
                context
            ),
            title = stringResource(io.wookoo.androidresources.R.string.humidity_prop),
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_speed,
            text = state.currentWeather.windSpeed.value.asLocalizedUnitValueString(
                state.currentWeather.windSpeed.unit,
                context
            ),
            title = stringResource(io.wookoo.androidresources.R.string.wind_speed_prop),
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_direction,
            text = stringResource(state.currentWeather.windDirection.asLocalizedString()),
            title = stringResource(io.wookoo.androidresources.R.string.wind_direction_prop)
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_gust,
            text = state.currentWeather.windGust.value.asLocalizedUnitValueString(
                state.currentWeather.windGust.unit,
                context
            ),
            title = stringResource(io.wookoo.androidresources.R.string.wind_gust_prop),

        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_precipitation,
            text = state.currentWeather.precipitation.value.asLocalizedUnitValueString(
                state.currentWeather.precipitation.unit,
                context
            ),
            title = stringResource(io.wookoo.androidresources.R.string.precipitation_prop),

        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_pressure_msl,
            text = state.currentWeather.pressureMsl.value.asLocalizedUnitValueString(
                state.currentWeather.pressureMsl.unit,
                context
            ),
            title = stringResource(io.wookoo.androidresources.R.string.pressure)
        )

        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_uv_index,
            text = state.currentWeather.uvIndex,
            title = stringResource(io.wookoo.androidresources.R.string.uv_index),

        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
private fun WeatherPropertiesPreview() {
    WeatherAppPortfolioTheme {
        WeatherProperties(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    humidity = WeatherValueWithUnit(
                        value = 28,
                        unit = WeatherUnit.PERCENT
                    ),
                    windSpeed = WeatherValueWithUnit(
                        value = 8,
                        unit = WeatherUnit.KMH
                    ),
                    windDirection = WindDirection.NORTH,
                    windGust = WeatherValueWithUnit(
                        value = 10,
                        unit = WeatherUnit.KMH
                    ),
                    precipitation = WeatherValueWithUnit(
                        10,
                        WeatherUnit.PERCENT
                    ),
                    pressureMsl = WeatherValueWithUnit(
                        1000,
                        WeatherUnit.PRESSURE
                    ),
                    uvIndex = "10"
                )
            )
        )
    }
}
