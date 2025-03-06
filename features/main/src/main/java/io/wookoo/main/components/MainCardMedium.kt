package io.wookoo.main.components

import android.content.res.Configuration
import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
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
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedGradientText
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.padding_50
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.size_170
import io.wookoo.main.R

@Composable
fun MainCardMedium(
    temperature: String,
    temperatureFeelsLike: String,
    @DrawableRes weatherImage: Int,
    @StringRes weatherName: Int,
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
                            text = stringResource(weatherName),
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
                        Modifier
                            .padding(large),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        SharedGradientText(
                            text = temperature,
                            style = MaterialTheme.typography.displayMedium,
                        )
                        SharedText(
                            color = Color.White,
                            text = stringResource(
                                io.wookoo.androidresources.R.string.feels_like,
                                temperatureFeelsLike
                            ),
                            style = MaterialTheme.typography.titleSmall,
                        )
                    }
                }
            }
        }
        Image(
            painter = painterResource(id = weatherImage),
            contentDescription = null,
            modifier = Modifier
                .size(size_170)
                .padding(large)
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ClearSkySunny0() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_clear_sky,
            temperatureFeelsLike = "32°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun PartlyCloudy1_or_2() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_partly_cloudy,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun Overcast3() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°C",
            weatherImage = io.wookoo.design.system.R.drawable.ic_overcast,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun Fog45_48() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_fog,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DrizzleLight51() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            weatherImage = io.wookoo.design.system.R.drawable.ic_drizzle_light,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun DrizzleModerate53() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            weatherImage = io.wookoo.design.system.R.drawable.ic_drizzle_moderate,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun DrizzleHeavy55() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            weatherImage = io.wookoo.design.system.R.drawable.ic_drizzle_heavy,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FreezingDrizzleLight56() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            weatherImage = io.wookoo.design.system.R.drawable.ic_freezing_drizzle_light,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FreezingDrizzleHeavy57() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            weatherImage = io.wookoo.design.system.R.drawable.ic_freezing_drizzle_heavy,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun RainLight61() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            weatherImage = io.wookoo.design.system.R.drawable.ic_rain_light,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RainModerate63() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            weatherImage = io.wookoo.design.system.R.drawable.ic_rain_moderate,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun HeavyRain65() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_rain_heavy,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun FreezingRainLight66() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_freezing_rain_light,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun FreezingRainLight67() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_freezing_rain_heavy,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SnowLight71() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_snow_light,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SnowModerate73() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_snow_moderate,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SnowHeavy75() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_snow_heavy,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SnowGrains77() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_snow_grains,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RainShowersLight80() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_rain_showers_light,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun RainShowersModerate81() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_rain_showers_moderate,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun RainShowersHeavy82() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_rain_showers_heavy,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun SnowShowersLight85() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_snow_showers_light,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun SnowShowersHeavy86() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_snow_showers_heavy,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_NO)
private fun ThunderStorm95() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_thunderstorm,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}

@Composable
@Preview(showBackground = false, uiMode = Configuration.UI_MODE_NIGHT_YES)
private fun ThunderStormHail96_99() {
    WeatherAppPortfolioTheme {
        MainCardMedium(
            temperature = "25°",
            weatherImage = io.wookoo.design.system.R.drawable.ic_thunderstorm_hail,
            weatherName =  io.wookoo.androidresources.R.string.clear_sky,
            temperatureFeelsLike = "32°"
        )
    }
}
