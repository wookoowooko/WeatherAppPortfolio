package io.wookoo.designsystem.ui.components

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBackIosNew
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.size_50
import io.wookoo.designsystem.ui.theme.small

@Composable
fun SharedAppBarButton(
    icon: ImageVector,
    modifier: Modifier = Modifier,
    tint: Color = MaterialTheme.colorScheme.onBackground,
    onClick: () -> Unit = {},
    enabled: Boolean = true
) {
    Surface(
        modifier = modifier.size(size_50),
        shape = rounded_shape_20_percent,
        color = MaterialTheme.colorScheme.background,
        shadowElevation = small,
    ) {
        IconButton(
            enabled = enabled,
            modifier = Modifier.fillMaxSize(),
            onClick = onClick
        ) {
            Icon(icon, contentDescription = null, tint = tint)
        }
    }
}

@Composable
@Preview(showBackground = false)
private fun AppBarButtonPreview() {
    SharedAppBarButton(
        onClick = {},
        icon = Icons.Default.ArrowBackIosNew
    )
}
