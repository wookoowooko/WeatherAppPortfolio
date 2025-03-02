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
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_50_percent
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.welcome.R
import io.wookoo.welcome.mvi.WelcomePageContract

@Composable
internal fun ChooseYourLocationCard(
    onIntent: (WelcomePageContract.OnIntent) -> Unit,
    state: WelcomePageContract.WelcomePageState,
) {
    Card(
        enabled = !state.isGeolocationSearchInProgress,
        onClick = {
            onIntent(WelcomePageContract.OnIntent.OnExpandedChange(true))
        },
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
            Icon(
                Icons.Default.LocationOn,
                null,
                modifier = Modifier.padding(medium)
            )
            SharedText(
                text = state.city.ifEmpty { stringResource(R.string.choose_your_location) },
                style = MaterialTheme.typography.titleMedium
            )
        }
    }
}

@Composable
@Preview
private fun ChooseYourLocationCardPreview() {
    ChooseYourLocationCard(
        state = WelcomePageContract.WelcomePageState(),
        onIntent = {}
    )
}
