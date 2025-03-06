package io.wookoo.domain.usecases

import io.wookoo.domain.units.ApiUnit
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class ApiUnitFormatUseCaseTest {

    private lateinit var useCase: UnitFormatUseCase

    @Before
    fun setUp() {
        useCase = UnitFormatUseCase()
    }

    @Test
    fun shouldFormatCelsius() {
        val expected = "19.4°C"
        val actual = useCase(19.4f, ApiUnit.CELSIUS)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatFahrenheit() {
        val expected = "19°F"
        val actual = useCase(19, ApiUnit.FAHRENHEIT)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatKmh() {
        val expected = "19km/h"
        val actual = useCase(19, ApiUnit.KMH)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatMs() {
        val expected = "19m/s"
        val actual = useCase(19, ApiUnit.MS)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatMph() {
        val expected = "55mp/h"
        val actual = useCase(55, ApiUnit.MPH)
        assertEquals(expected, actual)
    }



    @Test
    fun shouldFormatPercent() {
        val expected = "75%"
        val actual = useCase(75, ApiUnit.PERCENT)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatPressure() {
        val expected = "1013hPa"
        val actual = useCase(1013, ApiUnit.PRESSURE)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatMillimeters() {
        val expected = "10mm"
        val actual = useCase(10, ApiUnit.MM)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatCentimeters() {
        val expected = "50sm"
        val actual = useCase(50, ApiUnit.CM)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatInches() {
        val expected = "12inch"
        val actual = useCase(12, ApiUnit.INCH)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatFloatValues() {
        val expected = "3.5m/s"
        val actual = useCase(3.5f, ApiUnit.MS)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatDoubleValues() {
        val expected = "5.75km/h"
        val actual = useCase(5.75, ApiUnit.KMH)
        assertEquals(expected, actual)
    }

    @Test
    fun shouldFormatNegativeValues() {
        val expected = "-10°C"
        val actual = useCase(-10, ApiUnit.CELSIUS)
        assertEquals(expected, actual)
    }
}
