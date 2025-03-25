package io.wookoo.mappers.geocoding

import io.wookoo.domain.model.geocoding.GeocodingDomainUI
import io.wookoo.domain.model.geocoding.GeocodingResponseDomainUi
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingSearchDto

fun GeocodingResponseDto.asGeocodingResponseModel(): GeocodingResponseDomainUi {
    return GeocodingResponseDomainUi(
        results = results?.map { it.asGeocodingSearchModel() }.orEmpty()
    )
}

fun GeocodingSearchDto.asGeocodingSearchModel(): GeocodingDomainUI {
    return GeocodingDomainUI(
        cityName = name,
        latitude = latitude,
        longitude = longitude,
        countryName = country.orEmpty(),
        urbanArea = admin1,
        geoItemId = geoNameId
    )
}

fun ReverseGeocodingResponseDto.asReverseGeocodingResponseModel(): GeocodingResponseDomainUi {
    return GeocodingResponseDomainUi(
        results = geonames?.map { it.asReverseGeocodingSearchModel() }.orEmpty()
    )
}

fun ReverseGeocodingSearchDto.asReverseGeocodingSearchModel(): GeocodingDomainUI {
    return GeocodingDomainUI(
        cityName = cityName,
        latitude = 0.0,
        longitude = 0.0,
        countryName = countryName,
        urbanArea = areaName,
        geoItemId = geoNameId
    )
}
