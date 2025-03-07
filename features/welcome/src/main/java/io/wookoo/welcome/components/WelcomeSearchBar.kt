package io.wookoo.welcome.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.welcome.mvi.WelcomePageContract

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun WelcomeSearchBar(
    onIntent: (WelcomePageContract.OnIntent) -> Unit,
    searchQuery: String,
    state: WelcomePageContract.WelcomePageState,
    isLoading: Boolean,
) {
    SearchBar(
        modifier = Modifier,
        expanded = true,
        onExpandedChange = { expandState ->
            onIntent(WelcomePageContract.OnIntent.OnExpandedChange(expandState))
        },
        inputField = {
            SearchBarDefaults.InputField(
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchQuery.isNotEmpty()) {
                            onIntent(WelcomePageContract.OnIntent.OnSearchQueryChange(""))
                        } else {
                            onIntent(
                                WelcomePageContract.OnIntent.OnExpandedChange(
                                    false
                                )
                            )
                        }
                    }) {
                        Icon(
                            imageVector = Icons.Default.Clear,
                            contentDescription = null
                        )
                    }
                },
                query = searchQuery,
                onQueryChange = { query ->
                    onIntent(WelcomePageContract.OnIntent.OnSearchQueryChange(query))
                },
                onSearch = { query ->
                    onIntent(WelcomePageContract.OnIntent.OnSearchQueryChange(query))
                },
                placeholder = {
                    if (searchQuery.isEmpty()) {
                        Text(
                            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f),
                            text = stringResource(io.wookoo.androidresources.R.string.search_your_location),
                            modifier = Modifier
                        )
                    }
                },
                modifier = Modifier.fillMaxWidth(),
                expanded = true,
                onExpandedChange = { state ->
                    onIntent(WelcomePageContract.OnIntent.OnExpandedChange(state))
                },
            )
        }
    ) {
        if (state.results.isEmpty()) {
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
            SearchResults(state, onIntent)
        }
    }
}
