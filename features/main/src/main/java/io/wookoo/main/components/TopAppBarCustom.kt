package io.wookoo.main.components

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
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
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.designsystem.ui.components.SharedAppBarButton
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.rounded_shape_20_percent
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.main.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CustomSearchBar(
    isExpanded: Boolean,
    searchQuery: String,
    modifier: Modifier = Modifier,
    onSearchQueryChange: (String) -> Unit,
    onClose: () -> Unit,
    onIconClick: () -> Unit,
) {
    val textFieldFocusRequester = remember { FocusRequester() }

    LaunchedEffect(isExpanded) {
        if (isExpanded) {
            textFieldFocusRequester.requestFocus()
        }
    }

    AnimatedVisibility(
        modifier = modifier,
        visible = isExpanded,
        enter = slideInHorizontally(initialOffsetX = { -it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        Surface(
            modifier = Modifier
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                .fillMaxWidth()
                .padding(large),
            shape = rounded_shape_20_percent,
            color = MaterialTheme.colorScheme.background,
            shadowElevation = small,
        ) {
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                SharedAppBarButton(
                    enabled = false,
                    modifier = Modifier,
                    icon = Icons.Default.Search
                )
                Box(modifier = Modifier.weight(1f)) {
                    BasicTextField(
                        keyboardOptions = KeyboardOptions(
                            imeAction = ImeAction.Search
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = medium)
                            .focusRequester(textFieldFocusRequester),
                        value = searchQuery,
                        onValueChange = { onSearchQueryChange(it) },
                        textStyle = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.onSurface
                        ),
                        singleLine = true
                    )

                    if (searchQuery.isEmpty()) {
                        Text(
                            text = stringResource(R.string.search_your_location),
                            style = MaterialTheme.typography.labelSmall.copy(
                                color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.5f)
                            ),
                            modifier = Modifier.padding(horizontal = medium)
                        )
                    }
                }

                IconButton(onClick = {
                    if (searchQuery.isNotEmpty()) {
                        onSearchQueryChange("")
                    } else {
                        onClose()
                    }
                }) {
                    Icon(imageVector = Icons.Default.Clear, contentDescription = null)
                }
            }
        }
    }

    AnimatedVisibility(
        visible = !isExpanded,
        enter = slideInHorizontally(initialOffsetX = { it }) + fadeIn(),
        exit = slideOutHorizontally(targetOffsetX = { -it }) + fadeOut()
    ) {
        SharedAppBarButton(
            modifier = Modifier
                .windowInsetsPadding(TopAppBarDefaults.windowInsets)
                .padding(large),
            onClick = onIconClick,
            icon = Icons.Default.Search
        )
    }
}

@Composable
@Preview
private fun CustomSearchBarPreview() {
    CustomSearchBar(
        modifier = Modifier.fillMaxWidth(),
        onSearchQueryChange = { },
        searchQuery = "Moscow",
        onIconClick = { },
        isExpanded = true,
        onClose = {}
    )
}

@Composable
@Preview
private fun CustomSearchBarPreview2() {
    CustomSearchBar(
        modifier = Modifier.fillMaxWidth(),
        onSearchQueryChange = { },
        searchQuery = "Moscow",
        onIconClick = { },
        isExpanded = false,
        onClose = {}
    )
}
