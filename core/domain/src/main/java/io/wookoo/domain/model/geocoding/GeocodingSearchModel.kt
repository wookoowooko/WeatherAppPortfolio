package io.wookoo.domain.model.geocoding

data class GeocodingSearchModel(
    val name: String,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String,
    val country: String,
    val urbanArea: String? = null,
)
