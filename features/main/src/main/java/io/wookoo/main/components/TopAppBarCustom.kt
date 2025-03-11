package io.wookoo.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedAppBarButton
import io.wookoo.designsystem.ui.components.SharedLocationItem
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.domain.model.geocoding.GeocodingSearchModel

@OptIn(ExperimentalMaterial3Api::class, ExperimentalLayoutApi::class)
@Composable
fun SearchBarMain(
    searchQuery: String,
    isLoading: Boolean,
    isExpanded: Boolean,
    isGeolocationSearchInProgress: Boolean,
    results: List<GeocodingSearchModel>,
    modifier: Modifier = Modifier,
    onSearchNotExpandedIconClick: () -> Unit,
    onItemClick: (item: GeocodingSearchModel) -> Unit,
    onSearchQueryChange: (String) -> Unit,
    onClose: () -> Unit,
) {
    val textFieldFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            textFieldFocusRequester.requestFocus()
        }
    }

    AnimatedVisibility(
        modifier = modifier
            .windowInsetsPadding(
                WindowInsets.displayCutout.only(
                    WindowInsetsSides.Horizontal
                )
            ),
        visible = isExpanded,
        enter = slideInVertically() + fadeIn(),
    ) {
        SearchBar(
            colors = SearchBarDefaults.colors(
                containerColor = MaterialTheme.colorScheme.surface,
                dividerColor = MaterialTheme.colorScheme.surface,
            ),
            expanded = true,
            onExpandedChange = {},
            inputField = {
                SearchBarDefaults.InputField(
                    trailingIcon = {
                        IconButton(onClick = {
                            if (searchQuery.isNotEmpty()) {
                                onSearchQueryChange("")
                            } else {
                                onClose()
                            }
                        }) {
                            Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                        }
                    },
                    query = searchQuery,
                    onQueryChange = onSearchQueryChange,
                    onSearch = onSearchQueryChange,
                    placeholder = {
                        if (searchQuery.isEmpty()) {
                            Text(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                                text = stringResource(io.wookoo.androidresources.R.string.search_your_location),
                                modifier = Modifier
                            )
                        }
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .focusRequester(focusRequester = textFieldFocusRequester),
                    expanded = isExpanded,
                    onExpandedChange = {},
                )
            }
        ) {
            if (results.isEmpty()) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()

                ) {
                    if (isLoading) {
                        CircularProgressIndicator(
                            modifier = Modifier.align(Alignment.Center)
                        )
                    } else {
                        SharedText(
                            modifier = Modifier.fillMaxWidth(),
                            text = stringResource(io.wookoo.androidresources.R.string.no_results_found),
                            style = MaterialTheme.typography.labelLarge.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }
            } else {
                LazyColumn(
                    Modifier
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
                    items(results) { result ->
                        SharedLocationItem(
                            countryName = result.country,
                            cityName = result.cityName,
                            urbanArea = result.urbanArea,
                            onClick = {
                                onItemClick(result)
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
        }
    }

    AnimatedVisibility(
        modifier = Modifier
            .windowInsetsPadding(
                WindowInsets.displayCutout.only(
                    WindowInsetsSides.Horizontal
                )
            ),
        visible = !isExpanded,
        enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        SharedAppBarButton(
            enabled = !isGeolocationSearchInProgress,
            onClick = onSearchNotExpandedIconClick,
            modifier = Modifier
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                .padding(large),
            icon = Icons.Default.Search
        )
    }
}

val result = GeocodingSearchModel(
    cityName = "Moscow",
    latitude = 55.7558,
    longitude = 37.6176,
    countryCode = "RU",
    country = "Russia",
)

@Preview
@Composable
private fun SearchBarMainPreview() {
    SearchBarMain(
        onClose = {},
        onSearchNotExpandedIconClick = {},
        isExpanded = true,
        results = listOf(result),
        onItemClick = {},
        searchQuery = "Moscow",
        onSearchQueryChange = {},
        isLoading = false,
        isGeolocationSearchInProgress = false
    )
}

@Preview
@Composable
private fun SearchBarMainPreview2() {
    SearchBarMain(
        onClose = {},
        onSearchNotExpandedIconClick = {},
        isExpanded = false,
        results = emptyList(),
        onItemClick = {},
        searchQuery = "Moscow",
        onSearchQueryChange = {},
        isLoading = false,
        isGeolocationSearchInProgress = true
    )
}
