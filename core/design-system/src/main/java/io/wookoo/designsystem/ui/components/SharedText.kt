package io.wookoo.designsystem.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme

@Composable
fun SharedText(
    text: String,
    modifier: Modifier = Modifier,
    color: androidx.compose.ui.graphics.Color = MaterialTheme.colorScheme.onSurface,
    style: androidx.compose.ui.text.TextStyle = MaterialTheme.typography.headlineSmall,
    weight: FontWeight = FontWeight.Normal,
    maxLines: Int = Int.MAX_VALUE,
) {
    Text(
        overflow = TextOverflow.Ellipsis,
        maxLines = maxLines,
        fontWeight = weight,
        color = color,
        modifier = modifier,
        text = text,
        style = style
    )
}

@Composable
@Preview(showBackground = true)
private fun SharedTextPreview() {
    WeatherAppPortfolioTheme {
        SharedText(
            text = "America",
            color = MaterialTheme.colorScheme.primary
        )
    }
}
