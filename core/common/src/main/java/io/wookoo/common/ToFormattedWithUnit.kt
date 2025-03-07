package io.wookoo.common

import android.content.Context
import io.wookoo.domain.units.ApiUnit
import java.util.Locale

fun Number.asLocalizedUnitValueString(
    unit: ApiUnit?,
    context: Context,
): String {
    return String.format(
        Locale.getDefault(),
        "%d %s",
        this.toInt(),
        unit?.let { apiUnit ->
            getUnitString(apiUnit)
        }?.let { resId ->
            context.getString(resId)
        }
    )
}
