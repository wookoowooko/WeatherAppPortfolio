package io.wookoo.main.components

import androidx.annotation.StringRes
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedWeatherItem
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.main.R

@Composable
fun WeatherProperties(
    humidity: String,
    windSpeed: String,
    @StringRes windDirection: Int,
    windGust: String,
    precipitation: String,
    pressureMsl: String,
    uvIndex: String,
    modifier: Modifier = Modifier,
) {
    Row(modifier = modifier.horizontalScroll(rememberScrollState())) {
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_humidity,
            text = humidity,
            title = stringResource(R.string.humidity_prop),
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_speed,
            text = windSpeed,
            title = stringResource(R.string.wind_speed_prop),
        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_direction,
            text = stringResource(windDirection),
            title = stringResource(R.string.wind_direction_prop),

        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_wind_gust,
            text = windGust,
            title = stringResource(R.string.wind_gust_prop),

        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_precipitation,
            text = precipitation,
            title = stringResource(R.string.precipitation_prop),

        )
        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_pressure_msl,
            text = pressureMsl,
            title = stringResource(R.string.pressure),

        )

        SharedWeatherItem(
            modifier = Modifier.padding(medium),
            image = io.wookoo.design.system.R.drawable.ic_uv_index,
            text = uvIndex,
            title = stringResource(R.string.uv_index),

        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
private fun WeatherPropertiesPreview() {
    WeatherProperties(
        humidity = "28%",
        windSpeed = "8km/h",
        windDirection = io.wookoo.design.system.R.string.north,
        windGust = "10km/h",
        precipitation = "10%",
        pressureMsl = "1000 hPa",
        uvIndex = "10",
    )
}

@Composable
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
private fun WeatherPropertiesPreview2() {
    WeatherProperties(
        humidity = "28%",
        windSpeed = "8km/h",
        windDirection = io.wookoo.design.system.R.string.south,
        windGust = "10km/h",
        precipitation = "10%",
        pressureMsl = "1000 hPa",
        uvIndex = "10",
    )
}
