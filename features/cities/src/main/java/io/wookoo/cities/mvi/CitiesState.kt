package io.wookoo.cities.mvi

import io.wookoo.cities.uimodels.UiCity
import io.wookoo.domain.model.geocoding.GeocodingSearchModel

data class CitiesState(
    val bottomSheetExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val searchQuery: String = "",
    val cities: List<UiCity> = emptyList(),
    val results: List<GeocodingSearchModel> = emptyList(),
)
