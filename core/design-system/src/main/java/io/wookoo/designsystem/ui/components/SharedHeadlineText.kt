package io.wookoo.designsystem.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme

@Composable
fun SharedHeadlineText(
    text: String,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.colorScheme.onSurface,
    style: TextStyle = MaterialTheme.typography.displaySmall,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        fontWeight = FontWeight.Bold,
        color = color,
        modifier = modifier,
        text = text,
        style = style,
    )
}

@Composable
@Preview(showBackground = true)
private fun SharedHeadlineTextPreview() {
    WeatherAppPortfolioTheme {
        SharedHeadlineText(
            text = "London",
            color = MaterialTheme.colorScheme.primary
        )
    }
}
