package io.wookoo.designsystem.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.design.system.R
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_50_percent
import io.wookoo.designsystem.ui.theme.size_64
import io.wookoo.designsystem.ui.theme.small

@Composable
fun SharedHourlyComponent(
    @DrawableRes image: Int,
    text: String,
    timeText: String,
    isNow: Boolean,
    modifier: Modifier = Modifier,
) {
    val backgroundColor =
        if (isNow) MaterialTheme.colorScheme.secondary else Color.White

    Card(
        modifier = modifier,
        shape = rounded_shape_50_percent,
        colors = CardDefaults.cardColors(
            backgroundColor
        ),
        elevation = CardDefaults.cardElevation(
            defaultElevation = small
        )
    ) {
        Column(
            modifier = Modifier.padding(medium),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            SharedText(
                color = if (isNow) Color.White else Color.DarkGray.copy(0.6f),
                text = timeText,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(medium)
            )
            Image(
                painter = painterResource(id = image),
                contentDescription = null,
                modifier = Modifier.size(size_64)
            )
            SharedText(
                weight = FontWeight.Bold,
                color = if (isNow) Color.White else Color.DarkGray.copy(0.6f),
                text = text,
                style = MaterialTheme.typography.titleLarge,
                modifier = Modifier.padding(medium)
            )
        }
    }
}

@Composable
@Preview(showBackground = false)
private fun DailyComponentPreview() {
    WeatherAppPortfolioTheme {
        SharedHourlyComponent(
            image = R.drawable.ic_rain_heavy,
            text = "28%",
            timeText = "04:00",
            isNow = true
        )
    }
}

@Composable
@Preview(showBackground = false)
private fun DailyComponentPreview2() {
    WeatherAppPortfolioTheme {
        SharedHourlyComponent(
            image = R.drawable.ic_rain_heavy,
            text = "28%",
            timeText = "04:00",
            isNow = false
        )
    }
}
