package io.wookoo.domain.units

enum class WeatherUnit(val apiValue: String = "") {
    CELSIUS("celsius"),
    FAHRENHEIT("fahrenheit"),
    KMH("kmh"),
    MPH("mph"),
    HOUR,
    MINUTE,
    MS,
    PERCENT,
    PRESSURE,
    MM("mm"),
    INCH("inch"),
    CM
}
