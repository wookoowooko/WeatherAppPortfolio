package io.wookoo.domain.units

enum class ApiUnit(val symbol: String = "") {
    CELSIUS,
    FAHRENHEIT,
    KMH,
    HOUR,
    MINUTE,
    MS,
    MPH(" mp/h"),
    PERCENT,
    PRESSURE,
    MM,
    CM,
    INCH("inch"),
}
