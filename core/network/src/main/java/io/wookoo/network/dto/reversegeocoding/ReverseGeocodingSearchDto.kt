package io.wookoo.network.dto.reversegeocoding

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class ReverseGeocodingSearchDto(
    @SerialName("name")
    val name: String,
    @SerialName("toponymName")
    val toponymName: String,
    @SerialName("countryName")
    val countryName: String,
    @SerialName("adminName1")
    val areaName: String?,
)
