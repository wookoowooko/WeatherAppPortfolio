package io.wookoo.domain.model.reversegeocoding

data class ReverseGeocodingSearchModel(
    val geoItemId: Long,
    val name: String,
    val toponymName: String,
    val countryName: String,
    val areaName: String,
)
