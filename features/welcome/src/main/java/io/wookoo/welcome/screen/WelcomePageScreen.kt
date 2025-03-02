package io.wookoo.welcome.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
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
import io.wookoo.welcome.R
import io.wookoo.welcome.components.ChooseYourLocationCard
import io.wookoo.welcome.components.ContinueButton
import io.wookoo.welcome.components.DetectGeolocationCard
import io.wookoo.welcome.components.WelcomeSearchBar
import io.wookoo.welcome.mvi.WelcomePageContract

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun WelcomePageScreen(
    state: WelcomePageContract.WelcomePageState,
    onIntent: (WelcomePageContract.OnIntent) -> Unit,
) {
    val isSearchBarVisible = state.isSearchExpanded
    val searchQuery = state.searchQuery
    val isLoading = state.isLoading

    Scaffold(
        floatingActionButton = {
            if (!state.isGeolocationSearchInProgress) {
                if (state.city.isNotEmpty()) {
                    ContinueButton(onIntent)
                }
            }
        },
        floatingActionButtonPosition = FabPosition.Center,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            if (!isSearchBarVisible) {
                TopAppBar(
                    title = {
                        SharedText(
                            stringResource(R.string.choose_your_location)
                        )
                    }
                )
            } else {
                WelcomeSearchBar(onIntent, searchQuery, state, isLoading)
            }
        }
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(it),
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
                text = stringResource(R.string.or),
                style = MaterialTheme.typography.titleMedium.copy(
                    MaterialTheme.colorScheme.onBackground
                )
            )
            DetectGeolocationCard(onIntent, state)

            Box(
                contentAlignment = Alignment.Center,
                modifier = Modifier.weight(1f)
            ) {
                if (state.isGeolocationSearchInProgress) {
                    SharedLottieLoader(
                        modifier = Modifier.size(120.dp)
                    )
                } else {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                    ) {
                        SharedHeadlineText(
                            style = MaterialTheme.typography.displayMedium,
                            text = state.city,
                        )
                        SharedText(
                            text = state.country,
                        )
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
            state = WelcomePageContract.WelcomePageState(
                city = "Seoul",
                country = "Korea",
            ),
            onIntent = {}
        )
    }
}
