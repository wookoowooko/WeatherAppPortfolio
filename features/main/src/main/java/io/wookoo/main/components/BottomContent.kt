package io.wookoo.main.components


import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.pager.PagerState
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.snapshotFlow
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.wookoo.designsystem.ui.components.SharedSurfaceIcon
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.main.mvi.MainPageIntent
import io.wookoo.main.mvi.MainPageState
import io.wookoo.main.mvi.OnNavigateToWeekly
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
            .windowInsetsPadding(WindowInsets.navigationBars)
            .padding(small)
            .background(Color.Red),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier.padding(small),
        ) {
            SharedSurfaceIcon(
                onClick = {
                    onIntent(OnNavigateToWeekly(state.city))
                },
                iconPadding = small,
                icon = Icons.Default.AddLocation,
                modifier = Modifier.size(40.dp)
            )
        }



        LaunchedEffect(pagerState) {
            // Collect from the a snapshotFlow reading the currentPage
            snapshotFlow { pagerState.currentPage }.collect { page ->
                // Do something with each page change, for example:
                // viewModel.sendPageSelectedEvent(page)
                onIntent(SetPagerPosition(pagerState.currentPage))
            }
        }
        PagerIndicator(
            pageCount = state.cityListCount,
            currentPageIndex = pagerState.currentPage,
            modifier = Modifier
                .weight(1f)
                .padding(small),
            onPagerIndicatorClick = { pos ->
                scope.launch {
                    pagerState.animateScrollToPage(pos)
                }
            }
        )
    }
}

@Composable
@Preview
private fun BottomContentPreview() {
    BottomContent(
        onIntent = {},
        state = MainPageState(cityListCount = 1),
        pagerState = rememberPagerState(pageCount = { 2 }),
    )
}