package io.wookoo.common.ext

import androidx.annotation.StringRes
import io.wookoo.domain.units.WeatherUnit

@StringRes
fun WeatherUnit.asUnitString(): Int {
    return when (this) {
        WeatherUnit.CELSIUS -> io.wookoo.androidresources.R.string.celsius
        WeatherUnit.FAHRENHEIT -> io.wookoo.androidresources.R.string.unit_fahrenheit
        WeatherUnit.KMH -> io.wookoo.androidresources.R.string.unit_kmh
        WeatherUnit.MPH -> io.wookoo.androidresources.R.string.unit_mph
        WeatherUnit.MS -> io.wookoo.androidresources.R.string.unit_meter_sec
        WeatherUnit.PERCENT -> io.wookoo.androidresources.R.string.percent_unit
        WeatherUnit.PRESSURE -> io.wookoo.androidresources.R.string.pressure_unit
        WeatherUnit.MM -> io.wookoo.androidresources.R.string.millimeters_unit
        WeatherUnit.INCH -> io.wookoo.androidresources.R.string.inch_unit
        WeatherUnit.CM -> io.wookoo.androidresources.R.string.centimetres_unit
        WeatherUnit.MINUTE -> io.wookoo.androidresources.R.string.min_unit
        WeatherUnit.HOUR -> io.wookoo.androidresources.R.string.hour_unit
        else -> io.wookoo.androidresources.R.string.undetected
    }
}
