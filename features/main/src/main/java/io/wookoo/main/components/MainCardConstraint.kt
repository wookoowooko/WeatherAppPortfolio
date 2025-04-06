package io.wookoo.main.components

import android.content.res.Configuration
import androidx.compose.foundation.Image
import androidx.compose.foundation.MarqueeSpacing
import androidx.compose.foundation.background
import androidx.compose.foundation.basicMarquee
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.designsystem.ui.components.SharedGradientText
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.padding_50
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.size_170
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.main.mvi.MainPageState
import io.wookoo.models.ui.UiCurrentWeatherModel
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit

@Composable
fun MainCardConstraint(
    state: MainPageState,
    modifier: Modifier = Modifier,
) {
    val linearGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary.copy(0.6f),
        ),
    )
    val context = LocalContext.current

    ConstraintLayout(
        modifier = modifier.fillMaxWidth()
    ) {
        val (image, container, weatherStatus, temperature, feelsLikeText, feelsLikeTemperature) = createRefs()

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(size_170)
                .clip(shape = rounded_shape_20_percent)
                .background(linearGradient)
                .constrainAs(container) {
                    top.linkTo(parent.top, margin = padding_50)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                },
        ) {}

        SharedText(
            color = Color.White,
            text = state.currentWeather.temperatureFeelsLike.value.asLocalizedUnitValueString(
                state.currentWeather.temperatureFeelsLike.unit,
                context
            ),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.constrainAs(feelsLikeTemperature) {
                top.linkTo(temperature.bottom, margin = small)
                end.linkTo(parent.end, margin = large)
            }
        )

        SharedText(
            color = Color.White,
            text = stringResource(
                io.wookoo.androidresources.R.string.feels_like,
            ),
            style = MaterialTheme.typography.titleSmall,

            modifier = Modifier.constrainAs(
                feelsLikeText
            ) {
                end.linkTo(feelsLikeTemperature.start, margin = small)
                top.linkTo(temperature.bottom, margin = small)
            }
        )

        SharedGradientText(
            text = state.currentWeather.temperature.value.asLocalizedUnitValueString(
                state.currentWeather.temperature.unit,
                context
            ),
            style = MaterialTheme.typography.displayMedium,
            modifier = Modifier.constrainAs(
                temperature
            ) {
                end.linkTo(parent.end, margin = large)
                top.linkTo(container.top, margin = large)
            }
        )

        SharedHeadlineText(
            maxLines = 1,
            modifier = Modifier
                .padding(bottom = medium)
                .constrainAs(weatherStatus) {
                    bottom.linkTo(parent.bottom, margin = medium)
                    start.linkTo(image.start, margin = medium)
                }
                .basicMarquee(
                    iterations = Int.MAX_VALUE,
                    repeatDelayMillis = 300,
                    spacing = MarqueeSpacing(20.dp)
                ),
            color = Color.White,
            text = stringResource(
                state.currentWeather.weatherStatus.asLocalizedUiWeatherMap(
                    state.currentWeather.isDay
                ).second
            ),
            style = MaterialTheme.typography.headlineSmall,
        )

        Image(
            painter = painterResource(
                id = state.currentWeather.weatherStatus.asLocalizedUiWeatherMap(
                    state.currentWeather.isDay
                ).first
            ),
            contentDescription = null,
            modifier = Modifier
                .size(size_170)
                .padding(large)
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start, margin = medium)
                }
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ClearSkySunny0() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.CLEAR_SKY_0,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES, locale = "ru")
private fun TestRuLongText() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.FREEZING_DRIZZLE_LIGHT_56,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PartlyCloudy1_or_2() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.PARTLY_CLOUDY_1_OR_2,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Overcast3() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.OVERCAST_3,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun Fog45_48() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.FOG_45_OR_48,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DrizzleLight51() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.DRIZZLE_LIGHT_51,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun DrizzleModerate53() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.DRIZZLE_MODERATE_53,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DrizzleHeavy55() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.DRIZZLE_HEAVY_55,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FreezingDrizzleLight56() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.FREEZING_DRIZZLE_LIGHT_56,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FreezingDrizzleHeavy57() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.FREEZING_DRIZZLE_HEAVY_57,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun RainLight61() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.RAIN_LIGHT_61,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RainModerate63() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.RAIN_MODERATE_63,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun HeavyRain65() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.HEAVY_RAIN_65,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FreezingRainLight66() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.FREEZING_RAIN_LIGHT_66,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FreezingRainHeavy67() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.FREEZING_RAIN_HEAVY_67,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SnowLight71() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.SNOW_LIGHT_71,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SnowModerate73() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.SNOW_MODERATE_73,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SnowHeavy75() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.SNOW_HEAVY_75,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SnowGrains77() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.SNOW_GRAINS_77,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RainShowersLight80() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.RAIN_SHOWERS_LIGHT_80,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun RainShowersModerate81() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.RAIN_SHOWERS_MODERATE_81,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RainShowersHeavy82() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.RAIN_SHOWERS_HEAVY_82,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SnowShowersLight85() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.SNOW_SHOWERS_LIGHT_85,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SnowShowersHeavy86() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.SNOW_SHOWERS_HEAVY_86,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ThunderStorm95() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.THUNDERSTORM_95,
                )
            )
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ThunderStormHail96_99() {
    WeatherAppPortfolioTheme {
        MainCardConstraint(
            state = MainPageState(
                currentWeather = UiCurrentWeatherModel(
                    temperature = WeatherValueWithUnit(
                        value = 25.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    temperatureFeelsLike = WeatherValueWithUnit(
                        value = 32.0,
                        unit = WeatherUnit.CELSIUS
                    ),
                    weatherStatus = WeatherCondition.CLEAR_SKY_0,
                )
            )
        )
    }
}
