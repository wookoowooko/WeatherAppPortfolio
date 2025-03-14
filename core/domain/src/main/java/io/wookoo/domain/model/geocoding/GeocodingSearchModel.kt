package io.wookoo.domain.model.geocoding

data class GeocodingSearchModel(
    val geoNameId: Long,
    val cityName: String,
    val latitude: Double,
    val longitude: Double,
    val countryCode: String,
    val country: String,
    val urbanArea: String? = null,
)
