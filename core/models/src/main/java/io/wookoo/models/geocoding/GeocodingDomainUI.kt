package io.wookoo.models.geocoding

data class GeocodingDomainUI(
    val geoItemId: Long,
    val cityName: String,
    val latitude: Double? = null,
    val longitude: Double? = null,
    val countryName: String,
    val urbanArea: String? = null
)
