package io.wookoo.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.wookoo.domain.usecases.ConvertHourlyModelToHourlyListUseCaseTest.TestData.createTestHourlyModel
import io.wookoo.domain.usecases.ConvertHourlyModelToHourlyListUseCaseTest.TestData.createTestsUnits
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherUnits
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.weather.current.additional.HourlyModel
import io.wookoo.models.weather.current.additional.HourlyModelItem
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("VarCouldBeVal")
class ConvertHourlyModelToHourlyListUseCaseTest {

    @MockK
    private lateinit var convertUnixTimeUseCase: ConvertUnixTimeUseCase

    @MockK
    private lateinit var convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase

    @MockK
    private lateinit var defineCorrectUnitsUseCase: DefineCorrectUnitsUseCase

    private lateinit var convertHourlyModelToHourlyListUseCase: ConvertHourlyModelToHourlyListUseCase

    private lateinit var testWeatherUnits: WeatherUnits

    private lateinit var testHourlyModel: HourlyModel

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        convertHourlyModelToHourlyListUseCase = ConvertHourlyModelToHourlyListUseCase(
            convertUnixTimeUseCase, convertWeatherCodeToEnumUseCase, defineCorrectUnitsUseCase
        )

        testWeatherUnits = createTestsUnits()
        testHourlyModel = createTestHourlyModel()
    }

    @Test
    fun `should convert hourly model to hourly list`() = runTest {
        val utcOffsetSeconds = 3600L
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_LIGHT_51
        val expectedConvertedTime = listOf("10:00", "11:00")

        coEvery { defineCorrectUnitsUseCase.defineCorrectUnits() } returns testWeatherUnits
        every { convertUnixTimeUseCase.executeList(any(), any()) } returns expectedConvertedTime
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus

        val expectedHourlyList = listOf(
            HourlyModelItem(
                time = expectedConvertedTime[0],
                temperature = WeatherValueWithUnit(
                    value = 1.0f,
                    unit = testWeatherUnits.temperature
                ),
                weatherCode = expectedWeatherStatus,
                isNow = false,
                isDay = true
            ),
            HourlyModelItem(
                time = expectedConvertedTime[1],
                temperature = WeatherValueWithUnit(
                    value = 2.0f,
                    unit = testWeatherUnits.temperature
                ),
                weatherCode = expectedWeatherStatus,
                isNow = false,
                isDay = false
            )
        )

        val result = convertHourlyModelToHourlyListUseCase(testHourlyModel, utcOffsetSeconds)

        assertEquals(expectedHourlyList, result)
    }

    private object TestData {
        fun createTestsUnits() = WeatherUnits(
            temperature = WeatherUnit.FAHRENHEIT,
            precipitation = WeatherUnit.INCH,
            windSpeed = WeatherUnit.MPH
        )

        fun createTestHourlyModel() = HourlyModel(
            time = listOf(1L, 2L),
            temperature = listOf(1.0f, 2.0f),
            isDay = listOf(true, false),
            weatherCode = listOf(51, 51)
        )
    }
}
