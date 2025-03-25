package io.wookoo.weekly.uimodels

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.weekly.uimappers.asOtherPropsCardModel
import io.wookoo.weekly.uimappers.asUiCardInfoModel
import io.wookoo.weekly.uimappers.asUiPrecipitationCardModel
import io.wookoo.weekly.uimappers.asUiSunCyclesCardModel
import io.wookoo.weekly.uimappers.asUiWindCardModel

data class MainWeatherUiModel(
    val mainWeatherRecyclerItems: List<DisplayableItem> = emptyList(),
) {
    fun mapFromResponse(
        weekResponse: WeeklyWeatherDomainUI,
        selectedIndex: Int,
        convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
        formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
        convertUnixTimeUseCase: ConvertUnixTimeUseCase,
    ): MainWeatherUiModel {
        return copy(
            mainWeatherRecyclerItems = listOf(
                weekResponse.asUiCardInfoModel(
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
                    selectedCalendarItemIndex = selectedIndex
                ),
                weekResponse.asUiWindCardModel(
                    selectedCalendarItemIndex = selectedIndex,
                    formatWindDirectionUseCase = formatWindDirectionUseCase
                )
            )
        )
    }
}
