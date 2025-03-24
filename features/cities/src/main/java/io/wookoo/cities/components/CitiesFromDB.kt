package io.wookoo.cities.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.ui.unit.dp
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.cities.mvi.OnDeleteCity
import io.wookoo.cities.uimodels.UiCity
import io.wookoo.designsystem.ui.animateLazyItem
import io.wookoo.designsystem.ui.components.SharedDraggableBox
import io.wookoo.designsystem.ui.components.SharedSurfaceIcon
import io.wookoo.designsystem.ui.theme.large

@Composable
internal fun CitiesFromDB(
    state: CitiesState,
    modifier: Modifier = Modifier,
    onIntent: (CitiesIntent) -> Unit,
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(state.cities, key = { it.geoItemId }) { uiCity: UiCity ->
            var isDeleted by remember { mutableStateOf(false) }
            SharedDraggableBox(
                modifier = Modifier
                    .fillMaxWidth()
                    .animateLazyItem(this),
                firstContent = { mod ->
                    CityCard(
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
                            onIntent(OnDeleteCity(uiCity.geoItemId))
                        }
                    )
                },
                offsetSize = 100.dp,
                isDeleted = isDeleted,
                isCurrentLocation = uiCity.isCurrentLocation
            )
        }
    }
}

@Composable
@Preview
private fun CitiesFromDBPreview() {
    CitiesFromDB(
        state = CitiesState(),
        onIntent = { }
    )
}
