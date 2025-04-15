package io.wookoo.designsystem.ui.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import kotlinx.coroutines.delay

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedCustomSnackBar(
    message: String,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    snackBarColor: Color,
    modifier: Modifier = Modifier,
) {
    if (message.isNotEmpty()) {
        AnimatedVisibility(
            modifier = modifier,
            visible = isVisible,
            enter = slideInVertically { -it },
            exit = slideOutVertically { -it }
        ) {
            CenterAlignedTopAppBar(
                modifier = Modifier.fillMaxWidth(),
                colors = TopAppBarDefaults.topAppBarColors(containerColor = snackBarColor),
                title = {
                    SharedText(
                        text = message,
                        color = Color.White,
                        style = MaterialTheme.typography.titleMedium,
                    )
                },
                actions = {
                    IconButton(onClick = onDismiss) {
                        Icon(
                            Icons.Default.Close,
                            contentDescription = null,
                            tint = Color.White
                        )
                    }
                }
            )
        }
    }

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(3500)
            onDismiss()
        }
    }
}

@Preview
@Composable
private fun SharedCustomSnackBarPreview() {
    WeatherAppPortfolioTheme {
        SharedCustomSnackBar(
            message = "This is a custom snackbar",
            isVisible = true,
            onDismiss = {},
            snackBarColor = Color.Red
        )
    }
}
