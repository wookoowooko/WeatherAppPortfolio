package io.wookoo.welcome.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshotFlow
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
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.welcome.mvi.OnAppBarExpandChange
import io.wookoo.welcome.mvi.OnSearchQueryChange
import io.wookoo.welcome.mvi.WelcomePageIntent
import io.wookoo.welcome.mvi.WelcomePageState
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.debounce

@Composable
@OptIn(ExperimentalMaterial3Api::class, FlowPreview::class)
internal fun WelcomeSearchBar(
    onIntent: (WelcomePageIntent) -> Unit,

    state: WelcomePageState,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    val focusRequester = remember { FocusRequester() }

    var textFieldValue = remember(state.searchQuery) {
        TextFieldValue(
            text = state.searchQuery,
            selection = TextRange(state.searchQuery.length)
        )
    }


    LaunchedEffect(textFieldValue) {
        snapshotFlow { textFieldValue }
            .debounce(1000)
            .collect { latestValue ->
                val trimmed = latestValue.text.trim()
                if (trimmed != latestValue.text) {
                    val newValue = latestValue.copy(text = trimmed)
                    textFieldValue = newValue
                    onIntent(OnSearchQueryChange(newValue.text))
                } else {
                    onIntent(OnSearchQueryChange(latestValue.text))
                }
            }
    }

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }

    SearchBar(
        modifier = modifier
            .focusRequester(focusRequester),
        expanded = true,
        onExpandedChange = { expandState ->
            onIntent(OnAppBarExpandChange(expandState))
        },
        inputField = {
            TextField(
                colors = TextFieldDefaults.colors(
                    focusedContainerColor = Color.Transparent,
                    unfocusedContainerColor = Color.Transparent,
                    focusedIndicatorColor = Color.Transparent,
                    unfocusedIndicatorColor = Color.Transparent,
                ),
                value = textFieldValue,
                onValueChange = { newValue ->
                    textFieldValue = newValue
                    onIntent(OnSearchQueryChange(newValue.text))
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
                            onIntent(
                                OnAppBarExpandChange(false)
                            )
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
            SearchResults(state, onIntent)
        }
    }
}

@Composable
@Preview
private fun WelcomeSearchBarPreview() {
    WelcomeSearchBar(
        onIntent = {},
        state = WelcomePageState(),
        isLoading = false
    )
}
