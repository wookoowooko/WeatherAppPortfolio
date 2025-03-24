package io.wookoo.cities.mvi

import io.wookoo.cities.uimodels.UiCity
import io.wookoo.domain.model.geocoding.GeocodingModel

data class CitiesState(
    val isOffline: Boolean = false,
    val gpsItem: GeocodingModel? = null,
    val bottomSheetExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val searchQuery: String = "",
    val cities: List<UiCity> = emptyList(),
    val results: List<GeocodingModel> = emptyList(),
)
