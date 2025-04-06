package io.wookoo.welcome.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.utils.SingleClickHandler.singleClick
import io.wookoo.welcome.R
import io.wookoo.welcome.mvi.OnChangeUnitsDialogState
import io.wookoo.welcome.mvi.WelcomePageIntent

@Composable
fun UnitsButton(onIntent: (WelcomePageIntent) -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(
            containerColor = MaterialTheme.colorScheme.secondary,
        ),
        onClick = {
            singleClick {
                onIntent(OnChangeUnitsDialogState(true))
            }
        }
    ) {
        SharedText(
            text = stringResource(io.wookoo.androidresources.R.string.units_of_measurement_short),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Icon(Icons.Default.Settings, null)
    }
}

@Preview
@Composable
private fun UnitsButtonPreview() {
    UnitsButton(
        onIntent = {}
    )
}