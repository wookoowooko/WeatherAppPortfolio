package io.wookoo.cities.screen

import android.content.res.Configuration
import androidx.activity.compose.BackHandler
import androidx.compose.animation.Crossfade
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.add
import androidx.compose.foundation.layout.displayCutout
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import io.wookoo.cities.components.CitiesFromDB
import io.wookoo.cities.components.CitiesSearchBar
import io.wookoo.cities.components.DeleteCityDialog
import io.wookoo.cities.mvi.CitiesIntent
import io.wookoo.cities.mvi.CitiesState
import io.wookoo.cities.mvi.OnChangeBottomSheetVisibility
import io.wookoo.cities.mvi.OnChangeDeleteDialogVisibility
import io.wookoo.cities.mvi.OnDeleteCity
import io.wookoo.designsystem.ui.adaptive.isCompactDevice
import io.wookoo.designsystem.ui.components.SharedLottieLoader
import io.wookoo.designsystem.ui.components.SharedText
import io.wookoo.designsystem.ui.theme.WeatherAppPortfolioTheme
import io.wookoo.models.ui.UiCity
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherValueWithUnit
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun CitiesScreen(
    state: CitiesState,
    onIntent: (CitiesIntent) -> Unit,
    onBackIconClick: () -> Unit,
) {
    BackHandler(enabled = !state.isProcessing) { }
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    val scope = rememberCoroutineScope()

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = {
            TopAppBar(
                windowInsets = TopAppBarDefaults.windowInsets.add(
                    WindowInsets.displayCutout.only(
                        WindowInsetsSides.Horizontal
                    )
                ),
                navigationIcon = {
                    IconButton(
                        enabled = !state.isProcessing,
                        onClick = onBackIconClick
                    ) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null
                        )
                    }
                },
                title = {
                    SharedText(stringResource(io.wookoo.androidresources.R.string.locations))
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                modifier = Modifier.windowInsetsPadding(
                    WindowInsets.displayCutout.only(WindowInsetsSides.Horizontal)
                ),
                onClick = {
                    onIntent(OnChangeBottomSheetVisibility(true))
                }
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = null)
            }
        }
    ) {
        Crossfade(
            targetState = when {
                state.isLoading -> io.wookoo.designsystem.ui.Crossfade.LOADING
                else -> io.wookoo.designsystem.ui.Crossfade.CONTENT
            },
            label = ""
        ) { screenState ->
            when (screenState) {
                io.wookoo.designsystem.ui.Crossfade.LOADING -> SharedLottieLoader()
                io.wookoo.designsystem.ui.Crossfade.CONTENT -> {
                    val compactDevice = isCompactDevice()
                    CitiesFromDB(
                        state = state,
                        modifier = Modifier.padding(it),
                        onDeleteCity = { uiCity ->
                            if (compactDevice) {
                                onIntent(OnDeleteCity(uiCity.geoItemId))
                            } else {
                                onIntent(OnChangeDeleteDialogVisibility(true, uiCity))
                            }
                        }
                    )

                    if (state.deleteCityDialogState) {
                        state.city?.let { uiCity ->
                            DeleteCityDialog(
                                onIntent = onIntent,
                                city = uiCity
                            )
                        }
                    }

                    if (state.bottomSheetExpanded) {
                        ModalBottomSheet(
                            containerColor = MaterialTheme.colorScheme.surfaceContainerHigh,
                            modifier = Modifier.fillMaxSize(),
                            onDismissRequest = {
                                scope.launch { sheetState.hide() }.invokeOnCompletion {
                                    if (!sheetState.isVisible) {
                                        onIntent(OnChangeBottomSheetVisibility(false))
                                    }
                                }
                            },
                            sheetState = sheetState
                        ) {
                            CitiesSearchBar(
                                onIntent = onIntent,
                                state = state,
                                onHideBottomSheet = {
                                    scope.launch { sheetState.hide() }.invokeOnCompletion {
                                        if (!sheetState.isVisible) {
                                            onIntent(OnChangeBottomSheetVisibility(false))
                                        }
                                    }
                                }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
@Preview(
    name = "phone portrait - light",
    device = "spec:width=360dp,height=640dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_NO
)
@Preview(
    name = "phone portrait - dark",
    device = "spec:width=360dp,height=640dp,dpi=480",
    uiMode = Configuration.UI_MODE_NIGHT_YES
)
@Preview(name = "foldable portrait", device = "spec:width=673dp,height=841dp,dpi=480")
@Preview(name = "foldable landscape", device = "spec:width=841dp,height=673dp,dpi=480")
@Preview(name = "tablet landscape", device = "spec:width=1280dp,height=800dp,dpi=480")
@Preview(name = "tablet portrait", device = "spec:width=800dp,height=1280dp,dpi=480")
private fun CitiesScreenPreview() {
    WeatherAppPortfolioTheme {
        CitiesScreen(
            state = CitiesState(
                isLoading = false,
                cities = listOf(
                    UiCity(
                        weatherStatus = WeatherCondition.CLEAR_SKY_0,
                        cityName = "Лондон",
                        countryName = "UK",
                        temperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        isDay = true,
                        geoItemId = 1,
                        minTemperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        maxTemperature = WeatherValueWithUnit(
                            value = 21.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        date = "Sunday, 22 Mar.",
                        isCurrentLocation = true
                    ),
                    UiCity(
                        weatherStatus = WeatherCondition.SNOW_SHOWERS_HEAVY_86,
                        cityName = "Лондон",
                        countryName = "UK",
                        temperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        isDay = false,
                        geoItemId = 2,
                        minTemperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        maxTemperature = WeatherValueWithUnit(
                            value = 21.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        date = "Sunday, 22 Mar.",
                        isCurrentLocation = false
                    ),
                    UiCity(
                        weatherStatus = WeatherCondition.SNOW_HEAVY_75,
                        cityName = "Лондон",
                        countryName = "UK",
                        temperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        isDay = true,
                        geoItemId = 3,
                        minTemperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        maxTemperature = WeatherValueWithUnit(
                            value = 21.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        date = "Sunday, 22 Mar.",
                        isCurrentLocation = false
                    ),
                    UiCity(
                        weatherStatus = WeatherCondition.RAIN_LIGHT_61,
                        cityName = "Лондон",
                        countryName = "UK",
                        temperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        isDay = false,
                        geoItemId = 4,
                        minTemperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        maxTemperature = WeatherValueWithUnit(
                            value = 21.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        date = "Sunday, 22 Mar.",
                        isCurrentLocation = false
                    ),
                    UiCity(
                        weatherStatus = WeatherCondition.OVERCAST_3,
                        cityName = "Лондон",
                        countryName = "UK",
                        temperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        isDay = false,
                        geoItemId = 5,
                        minTemperature = WeatherValueWithUnit(
                            value = 18.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        maxTemperature = WeatherValueWithUnit(
                            value = 21.0,
                            unit = WeatherUnit.CELSIUS
                        ),
                        date = "Sunday, 22 Mar.",
                        isCurrentLocation = false
                    ),
                ),
            ),
            onIntent = {},
            onBackIconClick = {}
        )
    }
}
