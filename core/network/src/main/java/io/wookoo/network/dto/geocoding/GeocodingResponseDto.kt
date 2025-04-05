package io.wookoo.network.dto.geocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class GeocodingResponseDto(
    @SerialName("results")
    val results: List<GeocodingSearchDto>? = null
)