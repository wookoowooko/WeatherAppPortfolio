package io.wookoo.domain.model.geocoding

data class GeocodingModel(
    val geoItemId: Long,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val countryName: String,
    val urbanArea: String? = null,
)
