package io.wookoo.designsystem.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.design.system.R
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.size_100

@Composable
fun SharedWeatherItem(
    image: Int,
    text: String,
    title: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {},
) {
    Column(
        modifier = modifier.widthIn(size_100),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        SharedSurfaceIcon(
            image = image,
            onClick = onClick
        )
        SharedText(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(medium)
        )
        SharedText(
            text = text,
            style = MaterialTheme.typography.labelSmall,
            modifier = Modifier.padding(medium)
        )
    }
}

@Composable
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_NO)
private fun WeatherItemPreview() {
    SharedWeatherItem(
        onClick = {},
        image = R.drawable.ic_rain_heavy,
        text = "28%",
        title = "Humidity"
    )
}

@Composable
@Preview(showBackground = true, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
private fun WeatherItemPreview2() {
    SharedWeatherItem(
        onClick = {},
        image = R.drawable.ic_drizzle_light,
        text = "8km/h",
        title = "WindSpeed"
    )
}

@Composable
@Preview(showBackground = false, uiMode = android.content.res.Configuration.UI_MODE_NIGHT_YES)
private fun WeatherItemPreview3() {
    SharedWeatherItem(
        onClick = {},
        image = R.drawable.ic_drizzle_light,
        text = "8km/h",
        title = "WindSpeed",
    )
}
