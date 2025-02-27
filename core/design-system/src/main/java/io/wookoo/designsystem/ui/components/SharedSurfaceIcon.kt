package io.wookoo.designsystem.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import io.wookoo.design.system.R
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.size_80
import io.wookoo.designsystem.ui.theme.small

@Composable
fun SharedSurfaceIcon(

    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
    image: Int? = null,
    icon: ImageVector? = null,
    iconPadding: Dp = large,
) {
    Surface(
        modifier = modifier.size(size_80),
        shape = rounded_shape_20_percent,
        color = Color.White,
        shadowElevation = small,
    ) {
        IconButton(
            modifier = Modifier
                .fillMaxSize()
                .padding(iconPadding),
            onClick = onClick
        ) {
            if (icon != null) {
                Icon(imageVector = icon, contentDescription = null)
            }
            if (image != null) {
                Image(painter = painterResource(id = image), contentDescription = null)
            }
        }
    }
}

@Composable
@Preview
private fun SharedSurfaceIconPreview() {
    SharedSurfaceIcon(
        onClick = {},
        image = R.drawable.ic_rain_heavy
    )
}
