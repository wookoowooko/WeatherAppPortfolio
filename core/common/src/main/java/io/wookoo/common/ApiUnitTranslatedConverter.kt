package io.wookoo.common

import androidx.annotation.StringRes
import io.wookoo.domain.units.ApiUnit

@StringRes
fun getUnitString(unit: ApiUnit): Int {
    return when (unit) {
        ApiUnit.CELSIUS -> io.wookoo.androidresources.R.string.celsius
        ApiUnit.FAHRENHEIT -> io.wookoo.androidresources.R.string.unit_fahrenheit
        ApiUnit.KMH -> io.wookoo.androidresources.R.string.unit_kmh
        ApiUnit.MS -> io.wookoo.androidresources.R.string.unit_meter_sec
//        ApiUnit.MPH -> R.string.unit_mph
//        ApiUnit.DIRECTION -> R.string.unit_degree
        ApiUnit.PERCENT -> io.wookoo.androidresources.R.string.percent_unit
        ApiUnit.PRESSURE -> io.wookoo.androidresources.R.string.pressure_unit
        ApiUnit.MM -> io.wookoo.androidresources.R.string.millimeters_unit
        ApiUnit.CM -> io.wookoo.androidresources.R.string.centimetres_unit
        ApiUnit.MINUTE -> io.wookoo.androidresources.R.string.min_unit
        ApiUnit.HOUR -> io.wookoo.androidresources.R.string.hour_unit
//        ApiUnit.INCH -> R.string.unit_inch
        else -> io.wookoo.androidresources.R.string.undetected
    }
}
