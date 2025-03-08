package io.wookoo.welcome.components
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.NavigateNext
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.utils.SingleClickHandler.singleClick
import io.wookoo.welcome.mvi.OnContinueButtonClick
import io.wookoo.welcome.mvi.WelcomePageIntent

@Composable
fun ContinueButton(onIntent: (WelcomePageIntent) -> Unit) {
    Button(
        colors = ButtonDefaults.buttonColors(),
        onClick = {
            singleClick {
                onIntent(OnContinueButtonClick)
            }
        }
    ) {
        SharedText(
            text = stringResource(io.wookoo.androidresources.R.string.continue_string),
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onPrimary
        )
        Icon(Icons.AutoMirrored.Filled.NavigateNext, null)
    }
}

@Composable
@Preview
private fun ContinueButtonPreview() {
    ContinueButton(
        onIntent = {}
    )
}
