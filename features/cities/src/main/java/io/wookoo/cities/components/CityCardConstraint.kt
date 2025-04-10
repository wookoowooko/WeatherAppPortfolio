package io.wookoo.cities.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.common.ext.getWeatherGradient
import io.wookoo.designsystem.ui.adaptive.isCompactDevice
import io.wookoo.designsystem.ui.components.SharedGradientText
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.models.ui.UiCity
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit

@Composable
fun CityCardConstraint(
    uiCity: UiCity,
    modifier: Modifier = Modifier,
    onDeleteClick: () -> Unit = {},
) {
    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier
            .padding(medium)
            .fillMaxWidth()
            .clip(shape = rounded_shape_20_percent)
            .background(getWeatherGradient(uiCity.weatherStatus))
            .padding(bottom = medium)
    ) {
        val (
            cityName, countryName, uiCityDate, temp, min, minValue, max, maxValue,
            weatherIcon, weatherStatus, icon,
        ) = createRefs()

        SharedHeadlineText(
            modifier = Modifier.constrainAs(weatherStatus) {
                top.linkTo(weatherIcon.top)
                start.linkTo(weatherIcon.end, margin = medium)
                bottom.linkTo(weatherIcon.bottom)
            },
            color = Color.White,
            text = stringResource(
                uiCity.weatherStatus.asLocalizedUiWeatherMap(
                    isDay = uiCity.isDay
                ).second
            ),
            style = MaterialTheme.typography.titleMedium,
        )

        Image(
            painter = painterResource(
                id = uiCity.weatherStatus.asLocalizedUiWeatherMap(
                    isDay = uiCity.isDay,
                ).first
            ),
            contentDescription = null,
            modifier = Modifier
                .size(40.dp)
                .constrainAs(
                    weatherIcon
                ) {
                    start.linkTo(parent.start, margin = large)
                    top.linkTo(uiCityDate.bottom, margin = small)
                }

        )

        SharedText(
            color = Color.White,
            text = stringResource(
                io.wookoo.androidresources.R.string.max,
            ),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.constrainAs(max) {
                start.linkTo(min.start)
                top.linkTo(min.bottom)
            }
        )

        SharedText(
            modifier = Modifier
                .constrainAs(maxValue) {
                    start.linkTo(max.end, margin = medium)
                    top.linkTo(max.top)
                },
            color = Color.White,
            text = uiCity.maxTemperature.value.asLocalizedUnitValueString(
                uiCity.temperatureFeelsLike.unit,
                context
            ),
            style = MaterialTheme.typography.titleSmall,
        )

        SharedText(
            modifier = Modifier
                .constrainAs(minValue) {
                    top.linkTo(uiCityDate.top)
                    start.linkTo(min.end, margin = medium)
                }
                .padding(start = small),
            color = Color.White,
            text = uiCity.minTemperature.value.asLocalizedUnitValueString(
                uiCity.temperatureFeelsLike.unit,
                context
            ),
            style = MaterialTheme.typography.titleSmall,
        )

        SharedText(
            modifier = Modifier.constrainAs(min) {
                start.linkTo(temp.start)
                top.linkTo(uiCityDate.top)
            },
            color = Color.White,
            text = stringResource(
                io.wookoo.androidresources.R.string.min,
            ),
            style = MaterialTheme.typography.titleSmall,
        )

        SharedGradientText(
            text = uiCity.temperature.value.asLocalizedUnitValueString(
                uiCity.temperature.unit,
                context
            ),
            style = MaterialTheme.typography.displaySmall,
            modifier = Modifier.constrainAs(temp) {
                top.linkTo(countryName.top)
                end.linkTo(parent.end, margin = large)
                bottom.linkTo(countryName.bottom)
            },
        )

        SharedHeadlineText(
            modifier = Modifier
                .constrainAs(uiCityDate) {
                    start.linkTo(countryName.start)
                    top.linkTo(countryName.bottom, margin = medium)
                }
                .padding(bottom = medium),
            color = Color.White,
            text = uiCity.date,
            style = MaterialTheme.typography.titleSmall,
        )

        SharedHeadlineText(
            modifier = Modifier
                .constrainAs(countryName) {
                    start.linkTo(cityName.start)
                    top.linkTo(cityName.bottom, margin = medium)
                },
            color = Color.White,
            text = uiCity.countryName,
            style = MaterialTheme.typography.titleSmall,
        )

        SharedHeadlineText(
            maxLines = 1,
            modifier = Modifier
                .constrainAs(cityName) {
                    top.linkTo(parent.top, margin = large)
                    start.linkTo(parent.start, margin = large)
                }
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 300,
                    spacing = MarqueeSpacing(20.dp)
                ),
            color = Color.White,
            text = uiCity.cityName,
            style = MaterialTheme.typography.titleLarge,
        )

        if (uiCity.isCurrentLocation) {
            Icon(
                Icons.Default.NearMe,
                null,
                tint = Color.White,
                modifier = Modifier.constrainAs(icon) {
                    end.linkTo(parent.end, margin = large)
                    top.linkTo(weatherStatus.bottom)
                }
                    .size(40.dp)
            )
        } else if (!isCompactDevice()) {
            IconButton(
                colors = IconButtonDefaults.iconButtonColors(
                    containerColor = MaterialTheme.colorScheme.errorContainer,
                ),
                modifier = Modifier
                    .constrainAs(
                        icon
                    ) {
                        end.linkTo(parent.end, margin = large)
                        top.linkTo(weatherStatus.bottom)
                    }
                    .size(40.dp),
                onClick = { onDeleteClick() }
            ) {
                Icon(Icons.Default.Delete, null, tint = MaterialTheme.colorScheme.error)
            }
        }
    }
}

@Composable
@Preview
private fun CLEAR_SKY_0() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
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
                isCurrentLocation = true
            ),
        )
    }
}

@Composable
@Preview(name = "tablet portrait", device = "spec:width=800dp,height=1280dp,dpi=480")
private fun PARTLY_CLOUDY_1_OR_2() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.PARTLY_CLOUDY_1_OR_2,
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

@Composable
@Preview
private fun OVERCAST_3() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
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
private fun FOG_45_OR_48() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.FOG_45_OR_48,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 2,
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

@Composable
@Preview
private fun DRIZZLE_51_53_55() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.DRIZZLE_LIGHT_51,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 3,
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

@Composable
@Preview
private fun FREEZING_DRIZZLE_56_57() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.FREEZING_DRIZZLE_HEAVY_57,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 16,
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

@Composable
@Preview
private fun HEAVY_RAIN_61_63_65() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.HEAVY_RAIN_65,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 15,
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

@Composable
@Preview
private fun FREEZING_RAIN_LIGHT_66_67() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.FREEZING_RAIN_LIGHT_66,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 14,
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

@Composable
@Preview
private fun SNOW_LIGHT_71_73_75_77() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.SNOW_LIGHT_71,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 13,
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

@Composable
@Preview
private fun RAIN_SHOWERS_LIGHT_80_81_82() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.RAIN_SHOWERS_LIGHT_80,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 12,
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

@Composable
@Preview
private fun SNOW_SHOWERS_LIGHT_85_86() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.SNOW_SHOWERS_LIGHT_85,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 11,
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

@Composable
@Preview
private fun THUNDERSTORM_95_96_99() {
    WeatherAppPortfolioTheme {
        CityCardConstraint(
            uiCity = UiCity(
                weatherStatus = WeatherCondition.THUNDERSTORM_95,
                cityName = "Лондон",
                countryName = "UK",
                temperature = WeatherValueWithUnit(
                    value = 18.0,
                    unit = WeatherUnit.CELSIUS
                ),
                isDay = false,
                geoItemId = 10,
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
