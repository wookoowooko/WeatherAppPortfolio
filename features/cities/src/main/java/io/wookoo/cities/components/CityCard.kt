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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Icon
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
import io.wookoo.domain.units.WeatherUnit
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
//                .background(linearGradient),
                .background(getWeatherGradient(uiCity.weatherStatus)),
        ) {
            Column {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                ) {
                    Column(
                        modifier = Modifier
                            .weight(1f)
                            .padding(top = large)
                            .padding(horizontal = large)
                    ) {
                        SharedHeadlineText(
                            maxLines = 1,
                            modifier = Modifier
                                .padding(vertical = medium)
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
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = large)
                        .padding(bottom = medium),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Start
                ) {
                    Image(
                        painter = painterResource(
                            id = uiCity.weatherStatus.asLocalizedUiWeatherMap(
                                isDay = uiCity.isDay,
                            ).first
                        ),
                        contentDescription = null,
                        modifier = Modifier.size(40.dp)
                    )
                    SharedHeadlineText(
                        modifier = Modifier
                            .padding(start = medium)
                            .weight(1f),
                        color = Color.White,
                        text = stringResource(
                            uiCity.weatherStatus.asLocalizedUiWeatherMap(
                                isDay = uiCity.isDay
                            ).second
                        ),
                        style = MaterialTheme.typography.titleMedium,
                    )
                    if (uiCity.isCurrentLocation) {
                        Icon(Icons.Default.NearMe, null, tint = Color.White)
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
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                temperatureFeelsLike =
                WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = true,
                geoItemId = 1,
                date = "Sunday, 22 Mar.",
                isCurrentLocation = true
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
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 1,
                minTemperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                maxTemperature = WeatherValueWithUnit(
                    value = 21.0,
                    unit = WeatherUnit.CELSIUS
                ),
                date = "Sunday, 22 Mar.",
                isCurrentLocation = false
            ),
        )
    }
}

fun getWeatherGradient(condition: WeatherCondition): Brush {
    return Brush.linearGradient(
        colors = when (condition) {
            WeatherCondition.CLEAR_SKY_0 -> listOf(
                Color(0xFF87CEEB), // Light Sky Blue
                Color(0xFFADD8E6) // Light Blue
            )
            WeatherCondition.PARTLY_CLOUDY_1_OR_2 -> listOf(
                Color(0xFFB0E0E6), // Powder Blue
                Color(0xFF87CEEB) // Light Sky Blue
            )
            WeatherCondition.OVERCAST_3 -> listOf(
                Color(0xFF778899), // Light Slate Gray
                Color(0xFF708090) // Slate Gray
            )
            WeatherCondition.FOG_45_OR_48 -> listOf(
                Color(0xFF696969), // Dim Gray
                Color(0xFF808080) // Gray
            )
            WeatherCondition.DRIZZLE_LIGHT_51,
            WeatherCondition.DRIZZLE_MODERATE_53,
            WeatherCondition.DRIZZLE_HEAVY_55 -> listOf(
                Color(0xFF4682B4), // Steel Blue
                Color(0xFF5F9EA0) // Cadet Blue
            )
            WeatherCondition.FREEZING_DRIZZLE_LIGHT_56,
            WeatherCondition.FREEZING_DRIZZLE_HEAVY_57 -> listOf(
                Color(0xFF6A5ACD), // Slate Blue
                Color(0xFF483D8B) // Dark Slate Blue
            )
            WeatherCondition.RAIN_LIGHT_61,
            WeatherCondition.RAIN_MODERATE_63,
            WeatherCondition.HEAVY_RAIN_65 -> listOf(
                Color(0xFF4169E1), // Royal Blue
                Color(0xFF00008B) // Dark Blue
            )
            WeatherCondition.FREEZING_RAIN_LIGHT_66,
            WeatherCondition.FREEZING_RAIN_HEAVY_67 -> listOf(
                Color(0xFF000080), // Navy
                Color(0xFF191970) // Midnight Blue
            )
            WeatherCondition.SNOW_LIGHT_71,
            WeatherCondition.SNOW_MODERATE_73,
            WeatherCondition.SNOW_HEAVY_75,
            WeatherCondition.SNOW_GRAINS_77 -> listOf(
                Color(0xFFE0FFFF), // Light Cyan
                Color(0xFFAFEEEE) // Pale Turquoise
            )
            WeatherCondition.RAIN_SHOWERS_LIGHT_80,
            WeatherCondition.RAIN_SHOWERS_MODERATE_81,
            WeatherCondition.RAIN_SHOWERS_HEAVY_82 -> listOf(
                Color(0xFF1E90FF), // Dodger Blue
                Color(0xFF0000CD) // Medium Blue
            )
            WeatherCondition.SNOW_SHOWERS_LIGHT_85,
            WeatherCondition.SNOW_SHOWERS_HEAVY_86 -> listOf(
                Color(0xFFF0F8FF), // Alice Blue
                Color(0xFFE6E6FA) // Lavender
            )
            WeatherCondition.THUNDERSTORM_95,
            WeatherCondition.THUNDERSTORM_HAIL_96_OR_99 -> listOf(
                Color(0xFF00008B), // Dark Blue
                Color(0xFF000000) // Black
            )
            WeatherCondition.UNKNOWN -> listOf(
                Color(0xFFA9A9A9), // Dark Gray
                Color(0xFF808080) // Gray
            )
        }
    )
}
