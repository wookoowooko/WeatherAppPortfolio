package io.wookoo.welcome.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MyLocation
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.common.isLocationPermissionGranted
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_50_percent
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.welcome.R
import io.wookoo.welcome.mvi.WelcomePageContract

@Composable
internal fun DetectGeolocationCard(
    onIntent: (WelcomePageContract.OnIntent) -> Unit,
    state: WelcomePageContract.WelcomePageState,
) {
    val context = LocalContext.current

    Card(
        enabled = !state.isGeolocationSearchInProgress,
        onClick = {
            if (isLocationPermissionGranted(context)) {
                onIntent(WelcomePageContract.OnIntent.OnSearchGeoLocationClick)
            } else {
                onIntent(WelcomePageContract.OnIntent.OnRequestGeoLocationPermission)
            }
        },
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.primary
        ),
        shape = rounded_shape_50_percent,
        modifier = Modifier
            .fillMaxWidth()
            .padding(small)
            .padding(horizontal = large)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(small)

        ) {
            Icon(Icons.Default.MyLocation, null, modifier = Modifier.padding(medium))
            SharedText(
                text = stringResource(io.wookoo.androidresources.R.string.detect_automatically),
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier.padding(medium),
                color = MaterialTheme.colorScheme.onPrimary
            )
        }
    }
}

@Composable
@Preview
private fun DetectGeolocationCardPreview() {
    DetectGeolocationCard(
        onIntent = {},
        state = WelcomePageContract.WelcomePageState()
    )
}
