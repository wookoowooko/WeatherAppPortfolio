package io.wookoo.designsystem.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.designsystem.ui.theme.ultraSmall
import io.wookoo.designsystem.ui.utils.clickableSingle

@Composable
fun SharedLocationItem(
    countryName: String,
    cityName: String,
    urbanArea: String?,
    modifier: Modifier = Modifier,
    isCurrent: Boolean = false,
    enabled: Boolean = true,
    onClick: () -> Unit
) {
    ListItem(
        overlineContent = {
            SharedText(
                text = countryName,
                style = MaterialTheme.typography.labelSmall
            )
        },
        shadowElevation = small,
        modifier = modifier
            .padding(vertical = ultraSmall)
            .clickableSingle(enabled) {
                onClick()
            },
        tonalElevation = ultraSmall,
        headlineContent = {
            SharedText(text = cityName, style = MaterialTheme.typography.titleMedium)
        },
        supportingContent = {
            SharedText(
                text = urbanArea.orEmpty(),
                style = MaterialTheme.typography.bodySmall.copy(
                    color = MaterialTheme.colorScheme.onBackground.copy(alpha = 0.1f)
                )
            )
        },

        trailingContent = {
            if (isCurrent) {
                Icon(Icons.Default.NearMe, contentDescription = null)
            }
        }
    )
}

@Composable
@Preview
private fun LocationItemPreview() {
    WeatherAppPortfolioTheme {
        SharedLocationItem(
            countryName = "Korea",
            cityName = "Seoul",
            urbanArea = "Gangnam-gu",
            onClick = {}
        )
    }
}

@Composable
@Preview
private fun LocationItemPreview2() {
    WeatherAppPortfolioTheme {
        SharedLocationItem(
            countryName = "Korea",
            cityName = "Seoul",
            urbanArea = "Gangnam-gu",
            onClick = {},
            isCurrent = true
        )
    }
}

