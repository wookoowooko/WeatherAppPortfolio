package io.wookoo.domain.usecases

import io.wookoo.domain.enums.ApiUnit
import javax.inject.Inject
import kotlin.math.roundToInt

class UnitFormatUseCase @Inject constructor() {

    operator fun invoke(input: Number, unit: ApiUnit): String {
        val formattedValue =
            when (unit) {
                ApiUnit.CELSIUS, ApiUnit.FAHRENHEIT, ApiUnit.UV_INDEX -> {
                    (input.toFloat()).roundToInt()
                }

                else -> input
            }
        return StringBuilder()
            .append(formattedValue)
            .append(unit.symbol)
            .toString()
    }
}
