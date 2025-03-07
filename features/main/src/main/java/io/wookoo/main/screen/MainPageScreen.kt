package io.wookoo.main.screen

import android.util.Log
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.common.asLocalizedString
import io.wookoo.common.asLocalizedUiWeatherMap
import io.wookoo.common.asLocalizedUnitValueString
import io.wookoo.common.isLocationPermissionGranted
import io.wookoo.designsystem.ui.components.SharedHourlyComponent
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.ultraLarge
import io.wookoo.domain.model.weather.current.HourlyModelItem
import io.wookoo.main.components.Header
import io.wookoo.main.components.MainCardMedium
import io.wookoo.main.components.SearchBarMain
import io.wookoo.main.components.TodayRowTitle
import io.wookoo.main.components.WeatherProperties
import io.wookoo.main.mvi.MainPageContract

private const val TAG = "MainPageScreen"

@Composable
fun MainPageScreen(
    state: MainPageContract.MainPageState,
    onIntent: (MainPageContract.OnIntent) -> Unit,
) {
    val rowState = rememberLazyListState()
    var nowPosition by remember { mutableIntStateOf(0) }
    LaunchedEffect(state.currentWeather.hourlyList) {
        val newPosition = state.currentWeather.hourlyList.indexOfFirst { it.isNow }
        if (newPosition != -1) {
            nowPosition = newPosition
            rowState.animateScrollToItem(newPosition)
        }
    }
    val context = LocalContext.current
    val settings = state.userSettings
    val isGeolocationSearchInProgress = state.isGeolocationSearchInProgress

    LaunchedEffect(settings) {
        Log.d(TAG, "MainPageScreen: $settings")
    }

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
                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    topBar = {
                        SearchBarMain(
                            onSearchQueryChange = { query ->
                                onIntent(MainPageContract.OnIntent.OnSearchQueryChange(query))
                            },
                            onClose = { onIntent(MainPageContract.OnIntent.OnExpandSearchBar(false)) },
                            searchQuery = state.searchQuery,
                            onSearchNotExpandedIconClick = {
                                onIntent(MainPageContract.OnIntent.OnExpandSearchBar(true))
                            },
                            isExpanded = state.searchExpanded,
                            results = state.searchResults,
                            onItemClick = { geoItem ->
                                onIntent(MainPageContract.OnIntent.OnSearchedGeoItemClick(geoItem))
                            },
                            isLoading = state.isLoading,
                            isGeolocationSearchInProgress = isGeolocationSearchInProgress
                        )
                    }
                ) {
                    Column(
                        Modifier
                            .fillMaxWidth()
                            .windowInsetsPadding(
                                WindowInsets.displayCutout.only(
                                    WindowInsetsSides.Horizontal
                                )
                            )
                            .padding(it)
                            .verticalScroll(rememberScrollState(initial = 0), enabled = true),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Column(
                            Modifier
                                .fillMaxWidth(),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.Start
                        ) {
                            Header(
                                state = state,
                                modifier = Modifier.padding(horizontal = large),
                                sunriseTime = state.currentWeather.sunriseTime,
                                sunsetTime = state.currentWeather.sunsetTime,
                                city = state.city,
                                country = state.country,
                                onGeoLocationClick = {
                                    if (isLocationPermissionGranted(context)) {
                                        onIntent(MainPageContract.OnIntent.OnGeolocationIconClick)
                                    } else {
                                        onIntent(MainPageContract.OnIntent.OnRequestGeoLocationPermission)
                                    }
                                }
                            )
                            with(context) {
                                MainCardMedium(
                                    modifier = Modifier.padding(horizontal = large),
                                    temperature = state.currentWeather.temperature.value.asLocalizedUnitValueString(
                                        state.currentWeather.temperature.unit,
                                        this
                                    ),
                                    weatherName = state.currentWeather.weatherStatus.asLocalizedUiWeatherMap(
                                        state.currentWeather.isDay
                                    ).second,
                                    weatherImage = state.currentWeather.weatherStatus.asLocalizedUiWeatherMap(
                                        state.currentWeather.isDay
                                    ).first,
                                    temperatureFeelsLike = state.currentWeather.temperatureFeelsLike.value.asLocalizedUnitValueString(
                                        state.currentWeather.temperatureFeelsLike.unit,
                                        this
                                    ),
                                )
                            }
                            Spacer(modifier = Modifier.height(ultraLarge))
                            with(context) {
                                WeatherProperties(
                                    humidity = state.currentWeather.humidity.value.asLocalizedUnitValueString(
                                        state.currentWeather.humidity.unit,
                                        this
                                    ),
                                    windSpeed = state.currentWeather.windSpeed.value.asLocalizedUnitValueString(
                                        state.currentWeather.windSpeed.unit,
                                        this
                                    ),
                                    windDirection = state.currentWeather.windDirection.asLocalizedString(this),
                                    windGust = state.currentWeather.windGust.value.asLocalizedUnitValueString(
                                        state.currentWeather.windGust.unit,
                                        this
                                    ),
                                    precipitation = state.currentWeather.precipitation.value.asLocalizedUnitValueString(
                                        state.currentWeather.precipitation.unit,
                                        this
                                    ),
                                    pressureMsl = state.currentWeather.pressureMsl.value.asLocalizedUnitValueString(
                                        state.currentWeather.pressureMsl.unit,
                                        this
                                    ),
                                    uvIndex = state.currentWeather.uvIndex
                                )
                            }

                            TodayRowTitle(
                                modifier = Modifier.padding(horizontal = large),
                                onNextSevenDaysClick = {
                                    onIntent(MainPageContract.OnIntent.OnNavigateToWeekly)
                                },
                            )
                            Spacer(modifier = Modifier.height(ultraLarge))
                        }

                        LazyRow(
                            state = rowState
                        ) {
                            items(items = state.currentWeather.hourlyList) { item: HourlyModelItem ->
                                SharedHourlyComponent(
                                    modifier = Modifier.padding(medium),
                                    image = item.weatherCode.asLocalizedUiWeatherMap(isDay = item.isDay).first,
                                    text = item.temperature.value.asLocalizedUnitValueString(
                                        unit = item.temperature.unit,
                                        context = context
                                    ),
                                    timeText = item.time,
                                    isNow = item.isNow,
                                )
                            }
                        }
                    }
                }
            }

            else -> Unit
        }
    }
}

@Composable
@Preview
private fun MainPageScreenPreview() {
    WeatherAppPortfolioTheme {
        MainPageScreen(
            state = MainPageContract.MainPageState(),
            onIntent = {}
        )
    }
}
