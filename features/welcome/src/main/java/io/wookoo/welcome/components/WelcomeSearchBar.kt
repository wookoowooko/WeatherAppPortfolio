package io.wookoo.welcome.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
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
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.welcome.mvi.OnAppBarExpandChange
import io.wookoo.welcome.mvi.OnSearchQueryChange
import io.wookoo.welcome.mvi.WelcomePageIntent
import io.wookoo.welcome.mvi.WelcomePageState

@Composable
@OptIn(ExperimentalMaterial3Api::class)
internal fun WelcomeSearchBar(
    onIntent: (WelcomePageIntent) -> Unit,
    searchQuery: String,
    state: WelcomePageState,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    SearchBar(
        modifier = modifier,
        expanded = true,
        onExpandedChange = { expandState ->
            onIntent(OnAppBarExpandChange(expandState))
        },
        inputField = {
            SearchBarDefaults.InputField(
                modifier = Modifier
                    .fillMaxWidth()
                    .windowInsetsPadding(
                        WindowInsets.displayCutout.only(
                            WindowInsetsSides.Horizontal
                        )
                    ),
                trailingIcon = {
                    IconButton(onClick = {
                        if (searchQuery.isNotEmpty()) {
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
                },
                query = searchQuery,
                onQueryChange = { query ->
                    onIntent(OnSearchQueryChange(query))
                },
                onSearch = { query ->
                    onIntent(OnSearchQueryChange(query))
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

                expanded = true,
                onExpandedChange = { state ->
                    onIntent(OnAppBarExpandChange(state))
                },
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
