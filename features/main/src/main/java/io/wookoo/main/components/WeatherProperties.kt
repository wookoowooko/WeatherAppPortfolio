package io.wookoo.main.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedWeatherItem
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.domain.model.weather.current.CurrentWeatherUi
import io.wookoo.main.mvi.MainPageState

@Composable
fun WeatherProperties(
    state: MainPageState,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_humidity,
            text = state.currentWeather.humidity,
            title = stringResource(io.wookoo.androidresources.R.string.humidity_prop),
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_speed,
            text = state.currentWeather.windSpeed,
            title = stringResource(io.wookoo.androidresources.R.string.wind_speed_prop),
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_direction,
            text = state.currentWeather.windDirection,
            title = stringResource(io.wookoo.androidresources.R.string.wind_direction_prop)
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_gust,
            text = state.currentWeather.windGust,
            title = stringResource(io.wookoo.androidresources.R.string.wind_gust_prop),

        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_precipitation,
            text = state.currentWeather.precipitation,
            title = stringResource(io.wookoo.androidresources.R.string.precipitation_prop),

        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_pressure_msl,
            text = state.currentWeather.pressureMsl,
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
                currentWeather = CurrentWeatherUi(
                    humidity = "28",
                    windSpeed = "9",
                    windDirection = "North",
                    windGust = "10",
                    precipitation = "10",
                    pressureMsl = "1000",
                    uvIndex = "10"
                )
            )
        )
    }
}
