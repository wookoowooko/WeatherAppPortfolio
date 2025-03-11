package io.wookoo.designsystem.ui.components

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.layout.systemBars
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
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.IntOffset
import kotlinx.coroutines.delay
import kotlin.math.roundToInt

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SharedCustomSnackBar(
    message: String,
    isVisible: Boolean,
    onDismiss: () -> Unit,
    backGroundColor: Color = Color.Red,
) {
    val localDensity = LocalDensity.current
    val topAppBarPadding = TopAppBarDefaults.windowInsets.asPaddingValues().calculateTopPadding()
    val systemBars = WindowInsets.systemBars.asPaddingValues().calculateTopPadding()
    val statusBarHeight = WindowInsets.statusBars.asPaddingValues().calculateTopPadding()
    val finalHeight = topAppBarPadding + systemBars + statusBarHeight

    val animatedOffset by animateFloatAsState(
        targetValue = if (isVisible) 0f else -with(localDensity) { finalHeight.toPx() },
        label = "snackbarOffset"
    )

    CenterAlignedTopAppBar(
        modifier = Modifier.offset { IntOffset(0, animatedOffset.roundToInt()) },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = backGroundColor
        ),
        title = {
            SharedText(
                text = message,
                color = Color.White,
                style = MaterialTheme.typography.titleMedium,
            )
        },
        actions = {
            IconButton(onClick = onDismiss) {
                Icon(Icons.Default.Close, contentDescription = null)
            }
        }
    )

    LaunchedEffect(isVisible) {
        if (isVisible) {
            delay(3500)
            onDismiss()
        }
    }
}
