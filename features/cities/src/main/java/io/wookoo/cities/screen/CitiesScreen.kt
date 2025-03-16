package io.wookoo.cities.screen

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.components.CityCard
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme

private const val TAG = "CitiesScreen"

@Composable
internal fun CitiesScreen(
    state: CitiesState,
) {
    val context = LocalContext.current
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        floatingActionButton = {
            FloatingActionButton(onClick = {}) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
                .padding(it),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SharedText("Cities")
            LazyColumn(
                modifier = Modifier.fillMaxWidth()
            ) {
                items(state.cities) { uiCity ->
                    Log.d(TAG, "CitiesScreen: $uiCity")
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
    }
}

@Composable
@Preview
private fun CitiesScreenPreview() {
    WeatherAppPortfolioTheme {
        CitiesScreen(
            state = CitiesState()
        )
    }
}
