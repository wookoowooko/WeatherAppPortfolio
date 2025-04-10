package io.wookoo.cities.components

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.designsystem.ui.adaptive.Device
import io.wookoo.designsystem.ui.adaptive.Orientation
import io.wookoo.designsystem.ui.adaptive.rememberPane
import io.wookoo.designsystem.ui.animateLazyItem
import io.wookoo.designsystem.ui.components.SharedDraggableBox
import io.wookoo.designsystem.ui.components.SharedSurfaceIcon
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.models.ui.UiCity

@Composable
internal fun CitiesFromDB(
    state: CitiesState,
    modifier: Modifier = Modifier,
    onDeleteCity: (UiCity) -> Unit,
) {
    when (val pane = rememberPane()) {
        Device.Smartphone -> CompactPortrait(
            modifier = modifier,
            state = state,
            onDeleteCity = onDeleteCity
        )

        is Device.Tablet -> when (pane.orientation) {
            Orientation.Portrait -> TabletPortrait(
                modifier = modifier,
                state = state,
                onDeleteCity = onDeleteCity
            )

            Orientation.Landscape -> TabletLandscape(
                modifier = modifier,
                state = state,
                onDeleteCity = onDeleteCity
            )
        }
    }
}

@Composable
private fun CompactPortrait(
    state: CitiesState,
    modifier: Modifier = Modifier,
    onDeleteCity: (UiCity) -> Unit,
) {
    val extraSpace = WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal).asPaddingValues()
        .calculateEndPadding(
            layoutDirection = LayoutDirection.Ltr
        )
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(state.cities, key = { it.geoItemId }) { uiCity: UiCity ->
            var isDeleted by remember { mutableStateOf(false) }
            SharedDraggableBox(
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
                    )
                    .fillMaxWidth()
                    .animateLazyItem(this),
                firstContent = { mod ->
                    CityCardConstraint(
                        uiCity = uiCity,
                        modifier = mod,
                    )
                },
                secondContent = { mod ->
                    SharedSurfaceIcon(
                        iconTint = MaterialTheme.colorScheme.error,
                        surfaceColor = MaterialTheme.colorScheme.errorContainer,
                        modifier = mod
                            .width(100.dp)
                            .padding(end = large),
                        icon = Icons.Default.Delete,
                        onClick = {
                            isDeleted = true
                            onDeleteCity(uiCity)
                        }
                    )
                },
                offsetSize = 100.dp + extraSpace,
                isDeleted = isDeleted,
                isCurrentLocation = uiCity.isCurrentLocation
            )
        }
    }
}

@Composable
private fun TabletPortrait(
    state: CitiesState,
    modifier: Modifier = Modifier,
    onDeleteCity: (uiCity: UiCity) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(2),
        modifier = modifier.fillMaxWidth()
    ) {
        items(state.cities, key = { it.geoItemId }) { uiCity: UiCity ->
            CityCardConstraint(
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
                    ),
                uiCity = uiCity,
                onDeleteClick = {
                    onDeleteCity(uiCity)
                }
            )
        }
    }
}

@Composable
private fun TabletLandscape(
    state: CitiesState,
    modifier: Modifier = Modifier,
    onDeleteCity: (UiCity) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(3),
        modifier = modifier.fillMaxWidth()
    ) {
        items(state.cities, key = { it.geoItemId }) { uiCity: UiCity ->
            CityCardConstraint(
                modifier = Modifier
                    .windowInsetsPadding(
                        WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
                    ),
                uiCity = uiCity,
                onDeleteClick = {
                    onDeleteCity(uiCity)
                }
            )
        }
    }
}

@Composable
@Preview
private fun CitiesFromDBPreview() {
    CitiesFromDB(
        state = CitiesState(),
        onDeleteCity = {},
    )
}
