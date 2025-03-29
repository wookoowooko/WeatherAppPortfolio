package io.wookoo.mappers.geocoding

import io.wookoo.models.geocoding.GeocodingDomainUI
import io.wookoo.models.geocoding.GeocodingResponseDomainUi
import io.wookoo.network.dto.geocoding.GeocodingResponseDto
import io.wookoo.network.dto.geocoding.GeocodingSearchDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingResponseDto
import io.wookoo.network.dto.reversegeocoding.ReverseGeocodingSearchDto

fun GeocodingResponseDto.asGeocodingResponseModel(): io.wookoo.models.geocoding.GeocodingResponseDomainUi {
    return io.wookoo.models.geocoding.GeocodingResponseDomainUi(
        results = results?.map { it.asGeocodingSearchModel() }.orEmpty()
    )
}

fun GeocodingSearchDto.asGeocodingSearchModel(): io.wookoo.models.geocoding.GeocodingDomainUI {
    return io.wookoo.models.geocoding.GeocodingDomainUI(
        cityName = name,
        latitude = latitude,
        longitude = longitude,
        countryName = country.orEmpty(),
        urbanArea = admin1,
        geoItemId = geoNameId
    )
}

fun ReverseGeocodingResponseDto.asReverseGeocodingResponseModel(): io.wookoo.models.geocoding.GeocodingResponseDomainUi {
    return io.wookoo.models.geocoding.GeocodingResponseDomainUi(
        results = geonames?.map { it.asReverseGeocodingSearchModel() }.orEmpty()
    )
}

fun ReverseGeocodingSearchDto.asReverseGeocodingSearchModel(): io.wookoo.models.geocoding.GeocodingDomainUI {
    return io.wookoo.models.geocoding.GeocodingDomainUI(
        cityName = cityName,
        latitude = 0.0,
        longitude = 0.0,
        countryName = countryName,
        urbanArea = areaName,
        geoItemId = geoNameId
    )
}
