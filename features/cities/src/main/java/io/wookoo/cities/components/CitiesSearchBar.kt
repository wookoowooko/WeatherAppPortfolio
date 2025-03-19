package io.wookoo.cities.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.cities.mvi.OnSearchQueryChange
import io.wookoo.designsystem.ui.components.SharedText

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun CitiesSearchBar(
    state: CitiesState,
    onIntent: (CitiesIntent) -> Unit,
    onHideBottomSheet: () -> Unit,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    val textFieldValue = remember(state.searchQuery) {
        TextFieldValue(
            text = state.searchQuery,
            selection = TextRange(state.searchQuery.length)
        )
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchBar(
        modifier = modifier
            .focusRequester(focusRequester)
            .consumeWindowInsets(TopAppBarDefaults.windowInsets),
        expanded = true,
        onExpandedChange = {},
        inputField = {
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                value = textFieldValue,
                onValueChange = {
                    val trimmed = it.text.trim()
                    onIntent(OnSearchQueryChange(trimmed))
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(
                        WindowInsets.displayCutout.only(
                            WindowInsetsSides.Horizontal
                        ).add(
                            WindowInsets.navigationBars.only(
                                WindowInsetsSides.Horizontal
                            )
                        )
                    ),
                visualTransformation = VisualTransformation.None,
                placeholder = {
                    if (state.searchQuery.isEmpty()) {
                        Text(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            text = stringResource(io.wookoo.androidresources.R.string.search_your_location),
                            modifier = Modifier
                        )
                    }
                },
                trailingIcon = {
                    IconButton(onClick = {
                        if (state.searchQuery.isNotEmpty()) {
                            onIntent(OnSearchQueryChange(""))
                        } else {
                            onHideBottomSheet()
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                }
            )
        }
    ) {
        if (state.results.isEmpty()) {
            Box(
                modifier = Modifier.fillMaxWidth()
            ) {
                if (state.isLoading) {
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
            SearchResults(state, onIntent)
        }
    }
}

@Composable
@Preview
private fun WelcomeSearchBarPreview() {
    CitiesSearchBar(
        onIntent = {},
        state = CitiesState(),
        onHideBottomSheet = {}
    )
}
