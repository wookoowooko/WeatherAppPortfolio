package io.wookoo.designsystem.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme

@Composable
fun SharedGradientText(
    text: String,
    modifier: Modifier = Modifier,
    gradient: Brush = Brush.verticalGradient(
        colors = listOf(
            Color.White,
            Color.White.copy(0.5f)
        )
    ),
    style: TextStyle = MaterialTheme.typography.displaySmall,
) {
    Text(
        text = text,
        modifier = modifier,
        style = style.copy(
            brush = gradient
        )
    )
}

@Composable
@Preview
private fun GradientTextPreview() {
    WeatherAppPortfolioTheme {
        SharedGradientText(text = "Hello World")
    }
}
