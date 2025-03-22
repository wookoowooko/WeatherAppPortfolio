package io.wookoo.cities.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.wookoo.cities.uimodels.UiCity
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.designsystem.ui.components.SharedGradientText
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.domain.enums.WeatherCondition
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit

@Composable
fun CityCard(
    uiCity: UiCity,
    modifier: Modifier = Modifier,
) {
    val linearGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary.copy(0.6f),
        )
    )
    val context = LocalContext.current
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
            .padding(medium)
    ) {
        Box(
            modifier = Modifier
                .clip(shape = rounded_shape_20_percent)
                .background(linearGradient),
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                        .padding(large)
                ) {
                    SharedHeadlineText(
                        maxLines = 1,
                        modifier = Modifier.padding(vertical = medium)
                            .basicMarquee(
                                iterations = Int.MAX_VALUE,
                                repeatDelayMillis = 300,
                                spacing = MarqueeSpacing(20.dp)
                            ),
                        color = Color.White,
                        text = uiCity.cityName,
                        style = MaterialTheme.typography.titleLarge,
                    )
                    SharedHeadlineText(
                        modifier = Modifier.padding(bottom = medium),
                        color = Color.White,
                        text = uiCity.countryName,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    SharedHeadlineText(
                        modifier = Modifier.padding(bottom = medium),
                        color = Color.White,
                        text = uiCity.date,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(
                                id = uiCity.weatherStatus.asLocalizedUiWeatherMap(
                                    isDay = uiCity.isDay,
                                ).first
                            ),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                        )
                        SharedHeadlineText(
                            modifier = Modifier.padding(start = medium),
                            color = Color.White,
                            text = stringResource(
                                uiCity.weatherStatus.asLocalizedUiWeatherMap(
                                    isDay = uiCity.isDay
                                ).second
                            ),
                            style = MaterialTheme.typography.titleMedium,
                        )
                    }
                }

                Box(
                    modifier = Modifier
                        .align(Alignment.Top),
                    contentAlignment = Alignment.TopEnd
                ) {
                    Column(
                        Modifier.padding(large),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        SharedGradientText(
                            text = uiCity.temperature.value.asLocalizedUnitValueString(
                                uiCity.temperature.unit,
                                context
                            ),
                            style = MaterialTheme.typography.displaySmall,
                            modifier = Modifier.align(Alignment.End),
                        )

                        Row {
                            SharedText(
                                color = Color.White,
                                text = stringResource(
                                    io.wookoo.androidresources.R.string.min,
                                ),
                                style = MaterialTheme.typography.titleSmall,
                            )
                            SharedText(
                                modifier = Modifier.padding(start = small),
                                color = Color.White,
                                text = uiCity.minTemperature.value.asLocalizedUnitValueString(
                                    uiCity.temperatureFeelsLike.unit,
                                    context
                                ),
                                style = MaterialTheme.typography.titleSmall,
                            )
                            SharedText(
                                color = Color.White,
                                text = stringResource(
                                    io.wookoo.androidresources.R.string.max,
                                ),
                                style = MaterialTheme.typography.titleSmall,
                                modifier = Modifier.padding(start = large)
                            )
                            SharedText(
                                modifier = Modifier.padding(start = small),
                                color = Color.White,
                                text = uiCity.maxTemperature.value.asLocalizedUnitValueString(
                                    uiCity.temperatureFeelsLike.unit,
                                    context
                                ),
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview
private fun CityCardPreview() {
    WeatherAppPortfolioTheme {
        CityCard(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.OVERCAST_3,
                cityName = "Петропавловвско камчатский национальный парк",
//                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = ApiUnit.CELSIUS
                ),
                temperatureFeelsLike =
                WeatherValueWithUnit(
                    value = 18.0,
                    unit = ApiUnit.CELSIUS
                ),
                isDay = true,
                geoItemId = 1,
                date = "Sunday, 22 Mar."
            ),
        )
    }
}

@Composable
@Preview
private fun CityCardPreview2() {
    WeatherAppPortfolioTheme {
        CityCard(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.CLEAR_SKY_0,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = ApiUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 1,
                minTemperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = ApiUnit.CELSIUS
                ),
                maxTemperature = WeatherValueWithUnit(
                    value = 21.0,
                    unit = ApiUnit.CELSIUS
                ),
                date = "Sunday, 22 Mar."
            ),
        )
    }
}
