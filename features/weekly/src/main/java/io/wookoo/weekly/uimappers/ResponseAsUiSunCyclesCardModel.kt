package io.wookoo.weekly.uimappers

import io.wookoo.domain.model.weather.weekly.WeeklyWeatherResponseModel
import io.wookoo.domain.units.StringUnit
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.weekly.uimodels.UiSuncyclesModel

fun WeeklyWeatherResponseModel.asUiSunCyclesCardModel(
    selectedCalendarItemIndex: Int,
    convertUnixTimeUseCase: ConvertUnixTimeUseCase,
): UiSuncyclesModel {
    return UiSuncyclesModel(
        sunsetTime = StringUnit(
            convertUnixTimeUseCase.execute(
                this@asUiSunCyclesCardModel.weekly.sunCycles.sunrise[selectedCalendarItemIndex]
            )
        ),
        sunriseTime = StringUnit(
            convertUnixTimeUseCase.execute(
                this@asUiSunCyclesCardModel.weekly.sunCycles.sunset[selectedCalendarItemIndex]
            )
        )
    )
}
