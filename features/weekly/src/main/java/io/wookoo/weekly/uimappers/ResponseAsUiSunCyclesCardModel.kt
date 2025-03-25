package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.domain.units.StringUnit
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.weekly.uimodels.UiSuncyclesModel

fun WeeklyWeatherDomainUI.asUiSunCyclesCardModel(
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
