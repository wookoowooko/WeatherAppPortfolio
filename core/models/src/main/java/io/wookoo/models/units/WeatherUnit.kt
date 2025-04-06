package io.wookoo.models.units

enum class WeatherUnit(val apiValue: String = "") {
    CELSIUS("celsius"),
    FAHRENHEIT("fahrenheit"),
    KMH("kmh"),
    MPH("mph"),
    HOUR,
    MINUTE,
    MS("ms"),
    PERCENT,
    PRESSURE,
    MM("mm"),
    INCH("inch"),
    CM
}
