package io.wookoo.domain.usecases

import io.wookoo.domain.units.ApiUnit
import javax.inject.Inject
import kotlin.math.roundToInt

class UnitFormatUseCase @Inject constructor() {

    operator fun invoke(input: Number, unit: ApiUnit?): String {
        val formattedValue =
            when (unit) {
                ApiUnit.CELSIUS, ApiUnit.FAHRENHEIT -> {
                    (input.toFloat()).roundToInt()
                }

                else -> input
            }
        return StringBuilder()
            .append(formattedValue)
            .append(unit?.symbol)
            .toString()
    }
}
