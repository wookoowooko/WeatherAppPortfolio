package io.wookoo.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.designsystem.ui.components.SharedHourlyComponent
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.main.mvi.MainPageState
import io.wookoo.models.weather.current.additional.HourlyModelItem

@Composable
internal fun HourlyRow(
    state: MainPageState,
    modifier: Modifier = Modifier,
) {
    val context = LocalContext.current
    val rowState = rememberLazyListState()
    var nowPosition by remember { mutableIntStateOf(0) }

    LaunchedEffect(state.currentWeather.hourlyList) {
        val newPosition = state.currentWeather.hourlyList.indexOfFirst { pos -> pos.isNow }
        if (newPosition != -1) {
            nowPosition = newPosition
            rowState.scrollToItem(newPosition)
        }
    }

    LazyRow(
        modifier = modifier,
        state = rowState,
    ) {
        items(
            items = state.currentWeather.hourlyList,
            key = { item -> item.time }
        ) { item: HourlyModelItem ->
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

@Composable
@Preview
private fun HourlyRowPreview() {
    WeatherAppPortfolioTheme {
        HourlyRow(
            state = MainPageState()
        )
    }
}
