package io.wookoo.main.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.common.clickableSingle
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.designsystem.ui.theme.ultraSmall
import io.wookoo.domain.model.geocoding.GeocodingSearchModel

@Composable
fun LocationItem(
    result: GeocodingSearchModel,
    onClick: () -> Unit,
) {
    ListItem(
        overlineContent = {
            SharedText(
                text = result.country,
                style = MaterialTheme.typography.labelSmall
            )
        },
        shadowElevation = small,
        modifier = Modifier
            .padding(vertical = ultraSmall)
            .clickableSingle {
                onClick()
            },
        tonalElevation = ultraSmall,
        headlineContent = {
            SharedText(text = result.name, style = MaterialTheme.typography.titleMedium)
        },
        supportingContent = {
            SharedText(
                text = result.urbanArea.orEmpty(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                )
            )
        }
    )
}

@Composable
@Preview
private fun LocationItemPreview() {
    WeatherAppPortfolioTheme {
        LocationItem(result = result, onClick = {})
    }
}
