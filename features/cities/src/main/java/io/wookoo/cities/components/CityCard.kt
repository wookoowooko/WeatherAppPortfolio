package io.wookoo.cities.components

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.wookoo.designsystem.ui.components.SharedGradientText
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.small

@Composable
fun CityCard(
    cityName: String,
    countryName: String,
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
        )
    )

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
                        .padding(large)
                ) {
                    SharedHeadlineText(
                        modifier = Modifier.padding(vertical = medium),
                        color = Color.White,
                        text = cityName,
                        style = MaterialTheme.typography.headlineSmall,
                    )
                    SharedHeadlineText(
                        modifier = Modifier.padding(bottom = medium),
                        color = Color.White,
                        text = countryName,
                        style = MaterialTheme.typography.titleSmall,
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        Image(
                            painter = painterResource(id = weatherImage),
                            contentDescription = null,
                            modifier = Modifier
                                .size(40.dp)
                        )
                        SharedHeadlineText(
                            modifier = Modifier.padding(start = medium),
                            color = Color.White,
                            text = stringResource(weatherName),
                            style = MaterialTheme.typography.titleMedium,
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
                        horizontalAlignment = Alignment.Start
                    ) {
                        SharedGradientText(
                            text = temperature,
                            style = MaterialTheme.typography.displaySmall,
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
                                text = temperatureFeelsLike,
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
            cityName = "London",
            temperature = "20°C",
            countryName = "UK",
            temperatureFeelsLike = "18°C",
            weatherImage = io.wookoo.design.system.R.drawable.ic_rain_heavy,
            weatherName = io.wookoo.androidresources.R.string.clear_sky,
        )
    }
}
