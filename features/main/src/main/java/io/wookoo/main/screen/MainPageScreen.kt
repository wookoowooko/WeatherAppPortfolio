package io.wookoo.main.screen

import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.BottomAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.main.components.BottomContent
import io.wookoo.main.components.Header
import io.wookoo.main.components.HourlyRow
import io.wookoo.main.components.MainCardMedium
import io.wookoo.main.components.TodayRowTitle
import io.wookoo.main.components.WeatherProperties
import io.wookoo.main.mvi.MainPageIntent
import io.wookoo.main.mvi.MainPageState
import io.wookoo.main.mvi.OnNavigateToWeekly

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainPageScreen(
    state: MainPageState,
    onIntent: (MainPageIntent) -> Unit,
) {
    BackHandler(enabled = state.isGeolocationSearchInProgress) {}

    Crossfade(
        animationSpec = tween(1000),
        targetState = when {
            state.isLoading -> io.wookoo.designsystem.ui.Crossfade.LOADING
            else -> io.wookoo.designsystem.ui.Crossfade.CONTENT
        },
        label = ""
    ) { screenState ->
        when (screenState) {
            io.wookoo.designsystem.ui.Crossfade.LOADING -> SharedLottieLoader()
            io.wookoo.designsystem.ui.Crossfade.CONTENT -> {
                val pagerState = rememberPagerState(pageCount = {
                    state.cityListCount
                })

                Scaffold(
                    modifier = Modifier
                        .fillMaxSize(),
                    bottomBar = {
                        BottomAppBar {
                            BottomContent(onIntent, state, pagerState)
                        }
                    },
                    topBar = {
                        TopAppBar(
                            title = {
                                SharedText(
                                    stringResource(io.wookoo.androidresources.R.string.weather_app_bar_title)
                                )
                            }
                        )
                    }
                ) { paddings ->
                    HorizontalPager(
                        state = pagerState,
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(paddings)
                    ) {
                        LazyColumn(
                            Modifier
                                .fillMaxWidth()
                                .windowInsetsPadding(
                                    WindowInsets.displayCutout.only(
                                        WindowInsetsSides.Horizontal
                                    )
                                ),
                            verticalArrangement = Arrangement.Center,
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            item {
                                Column(
                                    Modifier
                                        .fillMaxWidth(),
                                    verticalArrangement = Arrangement.Center,
                                    horizontalAlignment = Alignment.Start
                                ) {
                                    Header(
                                        state = state,
                                        modifier = Modifier.padding(horizontal = large)
                                    )

                                    MainCardMedium(
                                        state = state,
                                        modifier = Modifier.padding(horizontal = large)
                                    )

                                    Spacer(modifier = Modifier.height(medium))
                                    WeatherProperties(state = state)
                                    TodayRowTitle(
                                        state = state,
                                        modifier = Modifier.padding(horizontal = large),
                                        onNextSevenDaysClick = {
                                            onIntent(OnNavigateToWeekly(geoItemId = state.currentWeather.geoNameId))
                                        },
                                    )
                                }
                            }
                            item {
                                HourlyRow(state, modifier = Modifier.padding(bottom = large))
                            }
                        }
                    }
                }
            }

            else -> Unit
        }
    }
}

@Composable
@Preview
private fun MainPageScreenPreview() {
    WeatherAppPortfolioTheme {
        MainPageScreen(
            state = MainPageState(isLoading = false),
            onIntent = {}
        )
    }
}
