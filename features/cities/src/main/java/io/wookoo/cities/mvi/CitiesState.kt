package io.wookoo.cities.mvi

import io.wookoo.cities.uimodels.UiCity
import io.wookoo.domain.model.geocoding.GeocodingSearchModel

data class CitiesState(
    val geoItemId: Long = -1,
    val bottomSheetExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val isSearchProcessing: Boolean = false,
    val searchQuery: String = "",
    val cities: List<UiCity> = emptyList(),
    val results: List<GeocodingSearchModel> = emptyList(),
)
