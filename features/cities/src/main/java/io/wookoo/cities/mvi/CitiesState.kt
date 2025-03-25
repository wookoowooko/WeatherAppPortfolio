package io.wookoo.cities.mvi

import io.wookoo.cities.uimodels.UiCity
import io.wookoo.domain.model.geocoding.GeocodingDomainUI

data class CitiesState(
    val isOffline: Boolean = false,
    val gpsItem: GeocodingDomainUI? = null,
    val bottomSheetExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val searchQuery: String = "",
    val cities: List<UiCity> = emptyList(),
    val results: List<GeocodingDomainUI> = emptyList(),
)
