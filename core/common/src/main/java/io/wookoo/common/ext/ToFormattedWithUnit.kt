package io.wookoo.common.ext

import android.content.Context
import io.wookoo.domain.units.WeatherUnit
import java.util.Locale

fun Number.asLocalizedUnitValueString(
    unit: WeatherUnit?,
    context: Context,
): String {
    return String.format(
        Locale.getDefault(),
        "%d %s",
        this.toInt(),
        unit?.asUnitString()?.let { resId ->
            context.getString(resId)
        }
    )
}
