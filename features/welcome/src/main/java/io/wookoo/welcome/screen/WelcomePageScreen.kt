package io.wookoo.welcome.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.domain.model.geocoding.GeocodingModel
import io.wookoo.welcome.components.ChooseYourLocationCard
import io.wookoo.welcome.components.ContinueButton
import io.wookoo.welcome.components.DetectGeolocationCard
import io.wookoo.welcome.components.WelcomeSearchBar
import io.wookoo.welcome.mvi.WelcomePageIntent
import io.wookoo.welcome.mvi.WelcomePageState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomePageScreen(
    state: WelcomePageState,
    onIntent: (WelcomePageIntent) -> Unit,
) {
    val isSearchBarVisible = state.isSearchExpanded

    Scaffold(
        floatingActionButton = {
            if (!state.isGeolocationSearchInProgress || !state.isLoading) {
                if (!state.geoItem?.cityName.isNullOrBlank()) {
                    ContinueButton(onIntent)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isSearchBarVisible) {
                TopAppBar(
                    windowInsets = (
                        TopAppBarDefaults.windowInsets.add(
                            WindowInsets.displayCutout.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                        ),
                    title = {
                        SharedText(
                            stringResource(io.wookoo.androidresources.R.string.choose_your_location)
                        )
                    }
                )
            } else {
                WelcomeSearchBar(onIntent, state)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it)
                .windowInsetsPadding(
                    WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                ),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Image(
                painter = painterResource(io.wookoo.design.system.R.drawable.search_stub),
                contentDescription = null,
                modifier = Modifier
                    .size(150.dp)
                    .padding(medium),
                contentScale = ContentScale.Crop,
            )
            ChooseYourLocationCard(onIntent, state)
            SharedText(
                text = stringResource(io.wookoo.androidresources.R.string.or),
                style = MaterialTheme.typography.titleMedium.copy(
                    MaterialTheme.colorScheme.onBackground
                )
            )
            DetectGeolocationCard(onIntent, state)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                if (state.isGeolocationSearchInProgress || state.isLoading) {
                    SharedLottieLoader(
                        modifier = Modifier.size(120.dp)
                    )
                } else {
                    state.geoItem?.let { geo ->
                        Column(
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.Center,
                        ) {
                            SharedHeadlineText(
                                style = MaterialTheme.typography.displayMedium,
                                text = geo.cityName,
                            )
                            SharedText(
                                text = geo.countryName,
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun WelcomePagePreview() {
    WeatherAppPortfolioTheme {
        WelcomePageScreen(
            state = WelcomePageState(
                geoItem = GeocodingModel(
                    cityName = "Seoul",
                    countryName = "Korea",
                    geoItemId = 1,
                    latitude = 0.0,
                    longitude = 0.0
                )
            ),
            onIntent = {}
        )
    }
}
