package io.wookoo.welcome.mvi

import io.wookoo.domain.model.geocoding.GeocodingModel
import io.wookoo.domain.utils.AppError

data class WelcomePageState(
    val isOffline: Boolean = false,
    val geoItem: GeocodingModel? = null,
    val isSearchExpanded: Boolean = false,
    val isLoading: Boolean = false,
    val errorMessage: AppError? = null,
    val searchQuery: String = "",
    val results: List<GeocodingModel> = emptyList(),
    val latitude: Double = 0.0,
    val longitude: Double = 0.0,
    val isGeolocationSearchInProgress: Boolean = false,
)
