package io.wookoo.cities.uimappers

import io.wookoo.cities.uimodels.UiCity
import io.wookoo.domain.model.weather.current.CurrentWeatherResponseModel
import io.wookoo.domain.units.ApiUnit
import io.wookoo.domain.units.WeatherValueWithUnit
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase

fun CurrentWeatherResponseModel.asUiCity(
    convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
): UiCity {
    return UiCity(
        cityName = this.cityName,
        countryName = this.countryName,
        temperature = WeatherValueWithUnit(
            value = this.current.temperature,
            unit = ApiUnit.CELSIUS
        ),
        temperatureFeelsLike = WeatherValueWithUnit(
            value = this.current.feelsLike,
            unit = ApiUnit.CELSIUS
        ),
        isDay = this.current.isDay,
        weatherStatus = convertWeatherCodeToEnumUseCase(this.current.weatherStatus),
        geoItemId = this.geoItemId,
    )
}
