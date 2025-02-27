package io.wookoo.main.screen

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.common.asStringRes
import io.wookoo.common.toUiWeather
import io.wookoo.designsystem.ui.components.SharedHourlyComponent
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.ultraLarge
import io.wookoo.main.components.CustomSearchBar
import io.wookoo.main.components.Header
import io.wookoo.main.components.MainCardMedium
import io.wookoo.main.components.TodayRowTitle
import io.wookoo.main.components.WeatherProperties
import io.wookoo.main.mvi.MainPageContract

@Composable
fun MainPageScreen(state: MainPageContract.MainPageState) {
    val rowState = rememberLazyListState()
    var nowPosition by remember { mutableIntStateOf(0) }
    LaunchedEffect(state.hourlyList) {
        val newPosition = state.hourlyList.indexOfFirst { it.isNow }
        if (newPosition != -1) {
            nowPosition = newPosition
            rowState.animateScrollToItem(newPosition)
        }
    }

    var isExpanded by remember { mutableStateOf(false) }
    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            CustomSearchBar(
                onSearchQueryChange = {},
                onClose = { isExpanded = false },
                searchQuery = "",
                onIconClick = {
                    isExpanded = true
                },
                isExpanded = isExpanded
            )
        }
    ) {
        Column(
            Modifier
                .fillMaxWidth()
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
                    sunsetTime = state.sunsetTime
                )
                MainCardMedium(
                    modifier = Modifier.padding(horizontal = large),
                    temperature = state.temperature,
                    weatherName = state.weatherStatus.toUiWeather(state.isDay).second,
                    weatherImage = state.weatherStatus.toUiWeather(state.isDay).first,
                    temperatureFeelsLike = state.temperatureFeelsLike
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
            if (state.isLoading) {
                CircularProgressIndicator()
            } else {
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
}

@Composable
@Preview
private fun MainPageScreenPreview() {
    WeatherAppPortfolioTheme {
        MainPageScreen(state = MainPageContract.MainPageState())
    }
}
