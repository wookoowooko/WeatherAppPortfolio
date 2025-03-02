package io.wookoo.network.dto.reversegeocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReverseGeocodingResponseDto(
    @SerialName("geonames")
    val geonames: List<ReverseGeocodingSearchDto>?,
)
