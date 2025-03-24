package io.wookoo.mappers.geocoding

import io.wookoo.domain.model.geocoding.GeocodingModel
import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.geocoding.ReverseGeocodingResponseModel
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingSearchDto

fun GeocodingResponseDto.asGeocodingResponseModel(): GeocodingResponseModel {
    return GeocodingResponseModel(
        results = results?.map { it.asGeocodingSearchModel() }.orEmpty()
    )
}

fun GeocodingSearchDto.asGeocodingSearchModel(): GeocodingModel {
    return GeocodingModel(
        cityName = name,
        latitude = latitude,
        longitude = longitude,
        countryName = country.orEmpty(),
        urbanArea = admin1,
        geoItemId = geoNameId
    )
}

fun ReverseGeocodingResponseDto.asReverseGeocodingResponseModel(): ReverseGeocodingResponseModel {
    return ReverseGeocodingResponseModel(
        geonames = geonames?.map { it.asReverseGeocodingSearchModel() }.orEmpty()
    )
}

fun ReverseGeocodingSearchDto.asReverseGeocodingSearchModel(): GeocodingModel {
    return GeocodingModel(
        cityName = cityName,
        latitude = 0.0,
        longitude = 0.0,
        countryName = countryName,
        urbanArea = areaName,
        geoItemId = geoNameId
    )
}
