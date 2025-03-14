package io.wookoo.network.dto.geocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class GeocodingSearchDto(
    @SerialName("id")
    val geoNameId: Long,
    @SerialName("name")
    val name: String,
    @SerialName("latitude")
    val latitude: Double,
    @SerialName("longitude")
    val longitude: Double,
    @SerialName("country_code")
    val countryCode: String,
    @SerialName("country")
    val country: String? = null,
    @SerialName("admin1")
    val admin1: String? = null,
)