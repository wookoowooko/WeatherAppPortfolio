package io.wookoo.mappers.geocoding

import io.wookoo.domain.model.geocoding.GeocodingResponseModel
import io.wookoo.domain.model.geocoding.GeocodingSearchModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingResponseModel
import io.wookoo.domain.model.reversegeocoding.ReverseGeocodingSearchModel
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingSearchDto

fun GeocodingResponseDto.asGeocodingResponseModel(): GeocodingResponseModel {
    return GeocodingResponseModel(
        results = results?.map { it.asGeocodingSearchModel() }.orEmpty()
    )
}

fun GeocodingSearchDto.asGeocodingSearchModel(): GeocodingSearchModel {
    return GeocodingSearchModel(
        cityName = name,
        latitude = latitude,
        longitude = longitude,
        countryCode = countryCode,
        country = country.orEmpty(),
        urbanArea = admin1,
        geoItemId = geoNameId
    )
}

fun ReverseGeocodingResponseDto.asReverseGeocodingResponseModel(): ReverseGeocodingResponseModel {
    return ReverseGeocodingResponseModel(
        geonames = geonames?.map { it.asReverseGeocodingSearchModel() }.orEmpty()
    )
}

fun ReverseGeocodingSearchDto.asReverseGeocodingSearchModel(): ReverseGeocodingSearchModel {
    return ReverseGeocodingSearchModel(
        name = name,
        toponymName = toponymName,
        countryName = countryName,
        areaName = areaName.orEmpty(),
        geoItemId = geoNameId
    )
}
