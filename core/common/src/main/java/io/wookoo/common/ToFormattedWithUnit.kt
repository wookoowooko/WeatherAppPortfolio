package io.wookoo.common

import io.wookoo.domain.units.ApiUnit
import java.util.Locale

fun Number.asUnitValueWithStringRes(unit: ApiUnit?, context: android.content.Context): String {
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
