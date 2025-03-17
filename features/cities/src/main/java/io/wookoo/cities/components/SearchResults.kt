package io.wookoo.cities.components


import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.imeNestedScroll
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.foundation.layout.windowInsetsBottomHeight
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.cities.mvi.OnSearchedGeoItemCardClick
import io.wookoo.designsystem.ui.components.SharedLocationItem
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium

@Composable
@OptIn(ExperimentalLayoutApi::class)
internal fun SearchResults(
    state: CitiesState,
    onIntent: (CitiesIntent) -> Unit,
) {
    LazyColumn(
        Modifier
            .windowInsetsPadding(
                WindowInsets.displayCutout.only(
                    WindowInsetsSides.Horizontal
                )
            )
            .imePadding()
            .imeNestedScroll()
            .fillMaxWidth()
            .padding(
                horizontal = large,
                vertical = medium
            ),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.Start
    ) {
        items(state.results) { result ->
            SharedLocationItem(
                countryName = result.country,
                cityName = result.cityName,
                urbanArea = result.cityName,
                onClick = {
                    onIntent(OnSearchedGeoItemCardClick(result))
                }
            )
        }
        item {
            Spacer(
                Modifier.windowInsetsBottomHeight(
                    WindowInsets.systemBars
                )
            )
        }
    }
}

@Composable
@Preview
private fun SearchResultsPreview() {
    SearchResults(
        state = CitiesState(),
        onIntent = {}
    )
}