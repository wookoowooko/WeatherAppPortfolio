package io.wookoo.cities.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString

@Composable
internal fun CitiesFromDB(state: CitiesState, modifier: Modifier = Modifier) {
    val context = LocalContext.current
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(state.cities) { uiCity ->

            CityCard(
                cityName = uiCity.cityName,
                temperature = uiCity.temperature.value.asLocalizedUnitValueString(
                    uiCity.temperature.unit,
                    context
                ),
                countryName = uiCity.countryName,
                temperatureFeelsLike = uiCity.temperatureFeelsLike.value.asLocalizedUnitValueString(
                    uiCity.temperatureFeelsLike.unit,
                    context
                ),
                weatherImage = uiCity.weatherStatus.asLocalizedUiWeatherMap(
                    isDay = uiCity.isDay,
                ).first,
                weatherName = uiCity.weatherStatus.asLocalizedUiWeatherMap(
                    isDay = uiCity.isDay
                ).second
            )
        }
    }
}

@Composable
@Preview
private fun CitiesFromDBPreview() {
    CitiesFromDB(
        state = CitiesState()
    )
}