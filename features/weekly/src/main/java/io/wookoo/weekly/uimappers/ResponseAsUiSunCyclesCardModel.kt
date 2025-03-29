package io.wookoo.weekly.uimappers

import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.models.units.StringUnit
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.weekly.uimodels.UiSuncyclesModel

fun io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI.asUiSunCyclesCardModel(
    selectedCalendarItemIndex: Int,
    convertUnixTimeUseCase: ConvertUnixTimeUseCase,
): UiSuncyclesModel {
    return UiSuncyclesModel(
        sunsetTime = io.wookoo.models.units.StringUnit(
            convertUnixTimeUseCase.execute(
                this@asUiSunCyclesCardModel.weekly.sunCycles.sunrise[selectedCalendarItemIndex],
                this@asUiSunCyclesCardModel.utcOffsetSeconds
            )
        ),
        sunriseTime = io.wookoo.models.units.StringUnit(
            convertUnixTimeUseCase.execute(
                this@asUiSunCyclesCardModel.weekly.sunCycles.sunset[selectedCalendarItemIndex],
                this@asUiSunCyclesCardModel.utcOffsetSeconds
            )
        )
    )
}
