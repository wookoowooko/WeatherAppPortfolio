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
import io.wookoo.common.asStringRes
import io.wookoo.common.isLocationPermissionGranted
import io.wookoo.common.toUiWeather
import io.wookoo.designsystem.ui.components.SharedHourlyComponent
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.ultraLarge
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
    LaunchedEffect(state.hourlyList) {
        val newPosition = state.hourlyList.indexOfFirst { it.isNow }
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
                                sunriseTime = state.sunriseTime,
                                sunsetTime = state.sunsetTime,
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
                            MainCardMedium(
                                modifier = Modifier.padding(horizontal = large),
                                temperature = state.temperature,
                                weatherName = state.weatherStatus.toUiWeather(state.isDay).second,
                                weatherImage = state.weatherStatus.toUiWeather(state.isDay).first,
                                temperatureFeelsLike = state.temperatureFeelsLike,
                            )
                            Spacer(modifier = Modifier.height(ultraLarge))
                            WeatherProperties(
                                humidity = state.humidity,
                                windSpeed = state.windSpeed,
                                windDirection = state.windDirection.asStringRes(),
                                windGust = state.windGust,
                                precipitation = state.precipitation,
                                pressureMsl = state.pressureMsl,
                                uvIndex = state.uvIndex
                            )
                            TodayRowTitle(
                                modifier = Modifier.padding(horizontal = large),
                                onNextSevenDaysClick = {},
                            )
                            Spacer(modifier = Modifier.height(ultraLarge))
                        }

                        LazyRow(
                            state = rowState
                        ) {
                            items(items = state.hourlyList) { item ->
                                SharedHourlyComponent(
                                    modifier = Modifier.padding(medium),
                                    image = item.weatherCode.toUiWeather(isDay = item.isDay).first,
                                    text = item.temperature,
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
