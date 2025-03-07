package io.wookoo.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedSurfaceIcon
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.size_40
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.designsystem.ui.theme.ultraSmall
import io.wookoo.main.mvi.MainPageContract

@Composable
internal fun Header(
    country: String,
    city: String,
    sunriseTime: String,
    sunsetTime: String,
    state: MainPageContract.MainPageState,
    modifier: Modifier = Modifier,
    onGeoLocationClick: () -> Unit,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f)
                .padding(end = medium)
        ) {
            Row(
                modifier = Modifier.padding(bottom = small),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = androidx.compose.ui.Alignment.CenterVertically
            ) {
                SharedSurfaceIcon(
                    onClick = onGeoLocationClick,
                    modifier = Modifier
                        .padding(end = small)
                        .size(size_40),
                    iconPadding = ultraSmall,
                    icon = Icons.Default.LocationOn,
                )
                SharedHeadlineText(
                    maxLines = 2,
                    text = city
                )
            }

            SharedText(
                text = country,
                maxLines = 2,
            )
            SharedText(
                style = MaterialTheme.typography.titleSmall,
                text = state.currentWeather.date,
                color = MaterialTheme.colorScheme.onSurface.copy(0.5f)
            )
        }
        Column(
            Modifier
        ) {
            Row(modifier = Modifier.padding(vertical = small)) {
                SharedSurfaceIcon(
                    modifier = Modifier
                        .size(size_40),
                    iconPadding = ultraSmall,
                    image = io.wookoo.design.system.R.drawable.ic_sunrise,
                    onClick = {}
                )
                Column(
                    modifier = Modifier.padding(horizontal = medium)
                ) {
                    SharedText(
                        text = stringResource(io.wookoo.androidresources.R.string.sunrise),
                        style = MaterialTheme.typography.titleSmall
                    )
                    SharedText(
                        text = sunriseTime,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
            Row(modifier = Modifier.padding(vertical = small)) {
                SharedSurfaceIcon(
                    modifier = Modifier.size(size_40),
                    iconPadding = ultraSmall,
                    image = io.wookoo.design.system.R.drawable.ic_sunset,
                    onClick = {}
                )
                Column(
                    modifier = Modifier.padding(horizontal = medium)
                ) {
                    SharedText(
                        text = stringResource(io.wookoo.androidresources.R.string.sunset),
                        style = MaterialTheme.typography.titleSmall
                    )
                    SharedText(
                        text = sunsetTime,
                        style = MaterialTheme.typography.titleSmall
                    )
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun HeaderPreview() {
    Header(
        state = MainPageContract.MainPageState(),
        sunriseTime = "06:00",
        sunsetTime = "18:00",
        city = "London",
        country = "UK",
        onGeoLocationClick = {}
    )
}
