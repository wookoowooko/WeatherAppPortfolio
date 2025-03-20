package io.wookoo.domain.model.geocoding

data class GeocodingSearchModel(
    val geoItemId: Long,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val country: String,
    val urbanArea: String? = null,
)
