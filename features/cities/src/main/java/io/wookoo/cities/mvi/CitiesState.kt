package io.wookoo.cities.mvi

import io.wookoo.cities.uimodels.UiCity
import io.wookoo.models.geocoding.GeocodingDomainUI

data class CitiesState(
    val isOffline: Boolean = false,
    val gpsItem: io.wookoo.models.geocoding.GeocodingDomainUI? = null,
    val bottomSheetExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val isProcessing: Boolean = false,
    val searchQuery: String = "",
    val cities: List<UiCity> = emptyList(),
    val results: List<io.wookoo.models.geocoding.GeocodingDomainUI> = emptyList(),
)
