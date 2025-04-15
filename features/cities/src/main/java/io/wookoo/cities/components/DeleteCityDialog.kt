/*
 * Copyright 2025  - Ruslan Gaivoronskii (aka wookoowookoo) https://github.com/wookoowooko
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package io.wookoo.cities.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.window.DialogProperties
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.OnChangeDeleteDialogVisibility
import io.wookoo.cities.mvi.OnDeleteCity
import io.wookoo.designsystem.ui.components.SharedHeadlineText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.size_40
import io.wookoo.models.ui.UiCity
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DeleteCityDialog(
    city: UiCity,
    modifier: Modifier = Modifier,
    onIntent: (CitiesIntent) -> Unit
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = {
            onIntent(OnChangeDeleteDialogVisibility(false))
        },
        properties = DialogProperties(
            dismissOnClickOutside = false,
            usePlatformDefaultWidth = false
        )
    ) {
        Card {
            SharedHeadlineText(
                stringResource(io.wookoo.androidresources.R.string.delete_city, city.cityName),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(large),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(large),
                horizontalArrangement = Arrangement.SpaceEvenly,
            ) {
                IconButton(
                    modifier = Modifier.size(size_40),
                    onClick = {
                        onIntent(OnDeleteCity(city.geoItemId))
                        onIntent(OnChangeDeleteDialogVisibility(false))
                    }
                ) {
                    Icon(
                        Icons.Default.Delete,
                        null,
                        tint = MaterialTheme.colorScheme.error,
                        modifier = Modifier.size(size_40)
                    )
                }
                IconButton(
                    modifier = Modifier.size(size_40),
                    onClick = {
                        onIntent(OnChangeDeleteDialogVisibility(false))
                    }
                ) {
                    Icon(
                        Icons.Default.Cancel,
                        null,
                        tint = MaterialTheme.colorScheme.tertiary,
                        modifier = Modifier.size(size_40)
                    )
                }
            }
        }
    }
}

@Composable
@Preview
private fun DeleteCityDialogPreview() {
    WeatherAppPortfolioTheme {
        DeleteCityDialog(
            onIntent = {},
            city = UiCity(
                cityName = "Moscow",
                countryName = "Russia",
                isCurrentLocation = false,
                date = "Sunday, 22 Mar.",
                temperature = WeatherValueWithUnit(
                    value = 20.0,
                    unit = WeatherUnit.CELSIUS,
                ),
                geoItemId = 1,
                isDay = false,
            )
        )
    }
}
