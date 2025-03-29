package io.wookoo.domain.usecases

import io.wookoo.models.ui.MainWeatherUiModel
import io.wookoo.models.ui.UIPrecipitationCardModel
import io.wookoo.models.ui.UIWindCardModel
import io.wookoo.models.ui.UiCardInfoModel
import io.wookoo.models.ui.UiOtherPropsModel
import io.wookoo.models.ui.UiSuncyclesModel
import io.wookoo.models.units.SecondsDuration
import io.wookoo.models.units.StringUnit
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherUnits
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import javax.inject.Inject
import kotlin.math.roundToInt

class MapWeeklyWeatherToUiUseCase @Inject constructor(
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    private val defineCorrectUnits: DefineCorrectUnitsUseCase,
) {
    suspend operator fun invoke(
        weekResponse: WeeklyWeatherDomainUI,
        selectedIndex: Int,
    ): MainWeatherUiModel {
        val units = defineCorrectUnits.defineCorrectUnits()
        return MainWeatherUiModel(
            mainWeatherRecyclerItems = listOf(
                weekResponse.asUiCardInfoModel(
                    units = units,
                    selectedCalendarItemIndex = selectedIndex,
                    convertWeatherCodeToEnumUseCase = convertWeatherCodeToEnumUseCase
                ),
                weekResponse.asUiSunCyclesCardModel(
                    selectedCalendarItemIndex = selectedIndex,
                    convertUnixTimeUseCase = convertUnixTimeUseCase
                ),
                weekResponse.asOtherPropsCardModel(
                    selectedCalendarItemIndex = selectedIndex
                ),
                weekResponse.asUiPrecipitationCardModel(
                    units = units,
                    selectedCalendarItemIndex = selectedIndex
                ),
                weekResponse.asUiWindCardModel(
                    units = units,
                    selectedCalendarItemIndex = selectedIndex,
                    formatWindDirectionUseCase = formatWindDirectionUseCase
                )
            )
        )
    }

    private fun WeeklyWeatherDomainUI.asUiWindCardModel(
        selectedCalendarItemIndex: Int,
        formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
        units: WeatherUnits,
    ): UIWindCardModel {
        return UIWindCardModel(
            windSpeed = WeatherValueWithUnit(
                this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].speed,
                units.windSpeed
            ),

            windGust = WeatherValueWithUnit(
                this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].gust,
                units.windSpeed
            ),
            windDirection = formatWindDirectionUseCase(
                this@asUiWindCardModel.weekly.windData[selectedCalendarItemIndex].direction
            ),
        )
    }

    private fun WeeklyWeatherDomainUI.asUiSunCyclesCardModel(
        selectedCalendarItemIndex: Int,
        convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    ): UiSuncyclesModel {
        return UiSuncyclesModel(
            sunsetTime = StringUnit(
                convertUnixTimeUseCase.execute(
                    this@asUiSunCyclesCardModel.weekly.sunCycles.sunrise[selectedCalendarItemIndex],
                    this@asUiSunCyclesCardModel.utcOffsetSeconds
                )
            ),
            sunriseTime = StringUnit(
                convertUnixTimeUseCase.execute(
                    this@asUiSunCyclesCardModel.weekly.sunCycles.sunset[selectedCalendarItemIndex],
                    this@asUiSunCyclesCardModel.utcOffsetSeconds
                )
            )
        )
    }

    private fun WeeklyWeatherDomainUI.asOtherPropsCardModel(
        selectedCalendarItemIndex: Int,
    ): UiOtherPropsModel {
        return UiOtherPropsModel(
            dayLightDuration = SecondsDuration(
                hour = WeatherValueWithUnit(
                    value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] / 3600),
                    unit = WeatherUnit.HOUR
                ),
                minute = WeatherValueWithUnit(
                    value = (this.weekly.dayLightDuration[selectedCalendarItemIndex] % 3600) / 60,
                    unit = WeatherUnit.MINUTE
                )
            ),
            sunShineDuration = SecondsDuration(
                hour = WeatherValueWithUnit(
                    value = (this.weekly.sunshineDuration[selectedCalendarItemIndex] / 3600),
                    unit = WeatherUnit.HOUR
                ),
                minute = WeatherValueWithUnit(
                    value = (this.weekly.sunshineDuration[selectedCalendarItemIndex] % 3600) / 60,
                    unit = WeatherUnit.MINUTE
                )
            ),
            maxUvIndex = this.weekly.uvIndexMax[selectedCalendarItemIndex].roundToInt().toString(),
        )
    }

    private fun WeeklyWeatherDomainUI.asUiCardInfoModel(
        selectedCalendarItemIndex: Int,
        convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
        units: WeatherUnits,
    ): UiCardInfoModel {
        return UiCardInfoModel(
            tempMax = WeatherValueWithUnit(
                this.weekly.tempMax[selectedCalendarItemIndex],
                units.temperature
            ),
            tempMin = this.weekly.tempMin[selectedCalendarItemIndex].toInt().toString(),
            feelsLikeMax = WeatherValueWithUnit(
                this.weekly.apparentTempMax[selectedCalendarItemIndex],
                units.temperature
            ),
            feelsLikeMin = WeatherValueWithUnit(
                this.weekly.apparentTempMin[selectedCalendarItemIndex],
                units.temperature
            ),
            weatherCondition = convertWeatherCodeToEnumUseCase(this.weekly.weatherCode[selectedCalendarItemIndex]),
            isDay = this.isDay
        )
    }

    private fun WeeklyWeatherDomainUI.asUiPrecipitationCardModel(
        selectedCalendarItemIndex: Int,
        units: WeatherUnits,
    ): UIPrecipitationCardModel {
        return UIPrecipitationCardModel(
            total = WeatherValueWithUnit(
                this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].level,
                units.precipitation
            ),
            rainSum = WeatherValueWithUnit(
                this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].rain,
                units.precipitation
            ),
            showersSum =
            WeatherValueWithUnit(
                this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].showers,
                units.precipitation
            ),
            snowSum = WeatherValueWithUnit(
                this@asUiPrecipitationCardModel.weekly.precipitationData[selectedCalendarItemIndex].snowfall,
                WeatherUnit.CM
            ),
            precipitationProbability = WeatherValueWithUnit(
                this@asUiPrecipitationCardModel.weekly.precipitationProbabilityMax[selectedCalendarItemIndex],
                WeatherUnit.PERCENT
            )
        )
    }
}
