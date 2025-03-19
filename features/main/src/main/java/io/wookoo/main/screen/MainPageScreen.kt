package io.wookoo.main.screen

import android.util.Log
import androidx.activity.compose.BackHandler
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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.common.ext.isFineLocationPermissionGranted
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.ultraLarge
import io.wookoo.main.components.BottomContent
import io.wookoo.main.components.Header
import io.wookoo.main.components.HourlyRow
import io.wookoo.main.components.MainCardMedium
import io.wookoo.main.components.TodayRowTitle
import io.wookoo.main.components.WeatherProperties
import io.wookoo.main.mvi.MainPageIntent
import io.wookoo.main.mvi.MainPageState
import io.wookoo.main.mvi.OnGeolocationIconClick
import io.wookoo.main.mvi.OnNavigateToWeekly
import io.wookoo.main.mvi.OnRequestGeoLocationPermission

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageScreen(
    state: MainPageState,
    onIntent: (MainPageIntent) -> Unit,
) {
//    val rowState = rememberLazyListState()
//
//    val hourlyList = state.currentWeather.hourlyList
//

    val context = LocalContext.current

    val isGeolocationSearchInProgress = state.isGeolocationSearchInProgress

//    val scope = rememberCoroutineScope()

    BackHandler(enabled = state.isGeolocationSearchInProgress) {}

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
                val pagerState = rememberPagerState(pageCount = {
                    state.cityListCount
                })

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = {
                        BottomAppBar {
                            BottomContent(onIntent, state, pagerState)
                        }
                    },
                    topBar = {
                        TopAppBar(
                            title = {
                                SharedText(stringResource(io.wookoo.androidresources.R.string.weather_app_bar_title))
                            }
                        )

// //                            SearchBarMain(
// //                                onSearchQueryChange = { query ->
// //                                    onIntent(OnSearchQueryChange(query))
// //                                },
// //                                onClose = { onIntent(OnExpandSearchBar(false)) },
// //                                searchQuery = state.searchQuery,
// //                                onSearchNotExpandedIconClick = {
// //                                    onIntent(OnExpandSearchBar(true))
// //                                },
// //                                isExpanded = state.searchExpanded,
// //                                results = state.searchResults,
// //                                onItemClick = { geoItem ->
// //                                    onIntent(OnSearchedGeoItemCardClick(geoItem))
// //                                },
// //                                isLoading = state.isLoading,
// //                                isGeolocationSearchInProgress = isGeolocationSearchInProgress
// //                            )
                    }
                ) { paddings ->
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddings)
                    ) {
                        LazyColumn(
                            Modifier
                                .fillMaxWidth()
                                .windowInsetsPadding(
                                    WindowInsets.displayCutout.only(
                                        WindowInsetsSides.Horizontal
                                    )
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
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
                                            if (context.isFineLocationPermissionGranted()) {
                                                onIntent(OnGeolocationIconClick)
                                            } else {
                                                onIntent(OnRequestGeoLocationPermission)
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
                                            windDirection = state.currentWeather.windDirection.asLocalizedString(
                                                this
                                            ),
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
                                        state = state,
                                        modifier = Modifier.padding(horizontal = large),
                                        onNextSevenDaysClick = {
                                            val latitude = state.currentWeather.latitude
                                            val longitude = state.currentWeather.longitude
                                            val geoNameId = state.currentWeather.geoNameId
                                            val cityName = state.city

                                            Log.d(TAG, "MainPageScreen: " +
                                                    "latitude: $latitude, " + "longitude: $longitude, " + "geoItemId: $geoNameId")
                                            onIntent(
                                                OnNavigateToWeekly(
                                                    latitude = latitude,
                                                    longitude = longitude,
                                                    geoItemId = geoNameId,
                                                    cityName = cityName
                                                )
                                            )
                                        },
                                    )
                                    Spacer(modifier = Modifier.height(ultraLarge))
                                }
                            }
                            item {
                                HourlyRow(state, modifier = Modifier.padding(bottom = large))
                            }
                        }
                    }
                }
            }

            else -> Unit
        }
    }
}
private const val TAG = "MainPageScreen"

@Composable
@Preview
private fun MainPageScreenPreview() {
    WeatherAppPortfolioTheme {
        MainPageScreen(
            state = MainPageState(isLoading = false),
            onIntent = {}
        )
    }
}
