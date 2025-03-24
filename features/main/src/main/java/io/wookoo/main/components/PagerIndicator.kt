package io.wookoo.main.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.NearMe
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.ultraSmall

@Composable
fun PagerIndicator(
    pageCount: Int,
    currentPageIndex: Int,
    modifier: Modifier = Modifier,
    onPagerIndicatorClick: (position: Int) -> Unit,
) {
    LazyRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {
        items(pageCount) { iteration ->
            val color = if (currentPageIndex == iteration) Color.DarkGray else Color.LightGray

            if (iteration == 0){
                Icon(Icons.Default.NearMe, null,
                    modifier = Modifier.size(large),
                    tint = color)
            } else{
                Box(
                    modifier = Modifier
                        .padding(ultraSmall)
                        .clip(CircleShape)
                        .background(color)
                        .size(medium)
                        .clickable { onPagerIndicatorClick(iteration) }
                )
            }
        }
    }
}

@Composable
@Preview
private fun PagerIndicatorPreview() {
    PagerIndicator(
        pageCount = 3,
        currentPageIndex = 1,
        onPagerIndicatorClick = {},
    )
}
