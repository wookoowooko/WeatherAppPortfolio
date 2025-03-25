package io.wookoo.domain.usecases

import io.wookoo.domain.units.WeatherUnit
import io.wookoo.domain.utils.IStringProvider
import javax.inject.Inject

class UnitFormatUseCase @Inject constructor(
    private val stringProvider: IStringProvider,
) {
    operator fun invoke(value: Number, unit: WeatherUnit) =
        StringBuilder()
            .append(value.toInt())
            .append(" ")
            .append(stringProvider.fromApiUnit(unit))
            .toString()
}
