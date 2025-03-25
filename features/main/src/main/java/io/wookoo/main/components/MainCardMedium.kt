package io.wookoo.main.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.designsystem.ui.components.SharedGradientText
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.padding_50
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.size_170
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.main.mvi.MainPageState

@Composable
fun MainCardMedium(
    state: MainPageState,
    modifier: Modifier = Modifier,
) {
    val linearGradient = Brush.linearGradient(
        colors = listOf(
            MaterialTheme.colorScheme.primary,
            MaterialTheme.colorScheme.primary.copy(0.6f),
        ),
    )

    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(Color.Transparent)
    ) {
        Box(
            modifier = Modifier
                .padding(top = padding_50)
                .height(size_170)
                .clip(shape = rounded_shape_20_percent)
                .background(linearGradient),
        ) {
            Row(
                modifier = Modifier.fillMaxSize(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .align(Alignment.Bottom),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(
                        Modifier
                            .padding(large),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.Start
                    ) {
                        SharedHeadlineText(
                            modifier = Modifier.padding(bottom = medium),
                            color = Color.White,
//                            text = stringResource(
//                                state.currentWeather.weatherStatus.asLocalizedUiWeatherMap(
//                                    state.currentWeather.isDay
//                                ).second
//                            ),
                            text = state.currentWeather.weatherStatus.first,
                            style = MaterialTheme.typography.headlineSmall,
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .align(Alignment.Top),
                    contentAlignment = Alignment.TopStart
                ) {
                    Column(
                        Modifier.padding(large),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SharedGradientText(
//                            text = state.currentWeather.temperature.value.asLocalizedUnitValueString(
//                                state.currentWeather.temperature.unit,
//                                context
//                            ),

                            text = state.currentWeather.temperature,
                            style = MaterialTheme.typography.displayMedium,
                        )
                        Row {
                            SharedText(
                                color = Color.White,
                                text = stringResource(
                                    io.wookoo.androidresources.R.string.feels_like,
                                ),
                                style = MaterialTheme.typography.titleSmall,
                            )
                            SharedText(
                                modifier = Modifier.padding(start = small),
                                color = Color.White,
//                                text = state.currentWeather.temperatureFeelsLike.value.asLocalizedUnitValueString(
//                                    state.currentWeather.temperatureFeelsLike.unit,
//                                    context
//                                ),
                                text = state.currentWeather.temperatureFeelsLike,
                                style = MaterialTheme.typography.titleSmall,
                            )
                        }
                    }
                }
            }
        }
        Image(
            painter = painterResource(
                id = state.currentWeather.weatherStatus.second.asLocalizedUiWeatherMap(
                    isDay = state.currentWeather.isDay
                ).first
            ),
            contentDescription = null,
            modifier = Modifier
                .size(size_170)
                .padding(large)
        )
    }
}
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun ClearSkySunny0() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.CLEAR_SKY_0,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun PartlyCloudy1_or_2() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.PARTLY_CLOUDY_1_OR_2,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun Overcast3() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.OVERCAST_3,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun Fog45_48() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.FOG_45_OR_48,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun DrizzleLight51() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.DRIZZLE_LIGHT_51,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun DrizzleModerate53() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.DRIZZLE_MODERATE_53,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun DrizzleHeavy55() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.DRIZZLE_HEAVY_55,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun FreezingDrizzleLight56() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.FREEZING_DRIZZLE_LIGHT_56,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun FreezingDrizzleHeavy57() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.FREEZING_DRIZZLE_HEAVY_57,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun RainLight61() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.RAIN_LIGHT_61,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun RainModerate63() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.RAIN_MODERATE_63,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun HeavyRain65() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.HEAVY_RAIN_65,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun FreezingRainLight66() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.FREEZING_RAIN_LIGHT_66,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun FreezingRainHeavy67() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.FREEZING_RAIN_HEAVY_67,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun SnowLight71() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.SNOW_LIGHT_71,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun SnowModerate73() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.SNOW_MODERATE_73,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun SnowHeavy75() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.SNOW_HEAVY_75,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun SnowGrains77() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.SNOW_GRAINS_77,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun RainShowersLight80() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.RAIN_SHOWERS_LIGHT_80,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun RainShowersModerate81() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.RAIN_SHOWERS_MODERATE_81,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun RainShowersHeavy82() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.RAIN_SHOWERS_HEAVY_82,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun SnowShowersLight85() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.SNOW_SHOWERS_LIGHT_85,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun SnowShowersHeavy86() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.SNOW_SHOWERS_HEAVY_86,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
// private fun ThunderStorm95() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.THUNDERSTORM_95,
//                )
//            )
//        )
//    }
// }
//
// @Composable
// @Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
// private fun ThunderStormHail96_99() {
//    WeatherAppPortfolioTheme {
//        MainCardMedium(
//            state = MainPageState(
//                currentWeather = UiCurrentWeatherModel(
//                    temperature = WeatherValueWithUnit(
//                        value = 25.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    temperatureFeelsLike = WeatherValueWithUnit(
//                        value = 32.0,
//                        unit = ApiUnit.CELSIUS
//                    ),
//                    weatherStatus = WeatherCondition.CLEAR_SKY_0,
//                )
//            )
//        )
//    }
// }
