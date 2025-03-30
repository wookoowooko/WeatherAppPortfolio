package io.wookoo.main.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Settings
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.wookoo.designsystem.ui.components.SharedSurfaceIcon
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.main.mvi.MainPageIntent
import io.wookoo.main.mvi.MainPageState
import io.wookoo.main.mvi.OnNavigateToCities
import io.wookoo.main.mvi.OnNavigateToSettings
import io.wookoo.main.mvi.SetPagerPosition
import kotlinx.coroutines.launch

@Composable
internal fun BottomContent(
    onIntent: (MainPageIntent) -> Unit,
    state: MainPageState,
    pagerState: PagerState,
) {
    val scope = rememberCoroutineScope()

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(small),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        LaunchedEffect(pagerState) {
            snapshotFlow { pagerState.currentPage }.collect { page ->
                onIntent(SetPagerPosition(page))
            }
        }

        Box(
            modifier = Modifier
                .padding(small),
        ) {
            SharedSurfaceIcon(
                onClick = {
                    onIntent(OnNavigateToSettings)
                },
                iconPadding = small,
                icon = Icons.Default.Settings,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterStart)
            )
        }

        PagerIndicator(
            pageCount = state.cityListCount,
            currentPageIndex = pagerState.currentPage,
            modifier = Modifier
                .weight(1f)
                .padding(medium),
            onPagerIndicatorClick = { pos ->
                scope.launch {
                    pagerState.scrollToPage(pos)
                }
            }
        )
        Box(
            modifier = Modifier
                .padding(small),
        ) {
            SharedSurfaceIcon(
                onClick = {
                    onIntent(OnNavigateToCities)
                },
                iconPadding = small,
                icon = Icons.Default.Menu,
                modifier = Modifier
                    .size(40.dp)
                    .align(Alignment.CenterEnd)
            )
        }
    }
}

@Composable
@Preview
private fun BottomContentPreview() {
    BottomContent(
        onIntent = {},
        state = MainPageState(cityListCount = 120),
        pagerState = rememberPagerState(pageCount = { 10 }),
    )
}
