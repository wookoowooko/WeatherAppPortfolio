package io.wookoo.welcome.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.androidresources.R
import io.wookoo.designsystem.ui.adaptive.Device
import io.wookoo.designsystem.ui.adaptive.Orientation
import io.wookoo.designsystem.ui.adaptive.rememberPane
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_50_percent
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.welcome.mvi.OnAppBarExpandChange
import io.wookoo.welcome.mvi.WelcomePageIntent
import io.wookoo.welcome.mvi.WelcomePageState

@Composable
internal fun ChooseYourLocationCard(
    state: WelcomePageState,
    onIntent: (WelcomePageIntent) -> Unit,
) {
    val cardModifier = when (val pane = rememberPane()) {
        is Device.Smartphone -> Modifier.fillMaxWidth()
        is Device.Tablet -> when (pane.orientation) {
            Orientation.Portrait -> Modifier.fillMaxWidth(0.7f)
            Orientation.Landscape -> Modifier.fillMaxWidth(0.7f)
        }
    }

    Card(
        enabled = !state.isGeolocationSearchInProgress || !state.isLoading,
        onClick = {
            onIntent(OnAppBarExpandChange(true))
        },
        shape = rounded_shape_50_percent,
        modifier = Modifier
            .padding(small)
            .padding(horizontal = large)
            .then(cardModifier)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth()
                .padding(small)
        ) {
            Icon(
                Icons.Default.LocationOn,
                null,
                modifier = Modifier.padding(medium)
            )

            val text =
                if (state.geoItem?.cityName.isNullOrEmpty()) {
                    stringResource(R.string.choose_your_location)
                } else {
                    state.geoItem?.cityName.orEmpty()
                }

            SharedText(
                text = text,
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@Preview
private fun ChooseYourLocationCardPreview() {
    ChooseYourLocationCard(
        state = WelcomePageState(),
        onIntent = {}
    )
}
