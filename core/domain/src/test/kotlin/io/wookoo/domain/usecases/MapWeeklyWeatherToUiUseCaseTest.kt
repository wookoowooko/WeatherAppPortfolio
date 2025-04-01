package io.wookoo.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.wookoo.domain.usecases.MapWeeklyWeatherToUiUseCaseTest.TestData.createTestWeeklyWeatherDomainUI
import io.wookoo.domain.usecases.MapWeeklyWeatherToUiUseCaseTest.TestData.createTestsUnits
import io.wookoo.models.ui.MainWeatherUiModel
import io.wookoo.models.ui.UIPrecipitationCardModel
import io.wookoo.models.ui.UIWindCardModel
import io.wookoo.models.ui.UiCardInfoModel
import io.wookoo.models.ui.UiOtherPropsModel
import io.wookoo.models.ui.UiSuncyclesModel
import io.wookoo.models.units.SecondsDuration
import io.wookoo.models.units.StringUnit
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherUnits
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.units.WindDirection
import io.wookoo.models.weather.current.additional.PrecipitationModel
import io.wookoo.models.weather.current.additional.SunCyclesModel
import io.wookoo.models.weather.current.additional.WindModel
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.models.weather.weekly.additional.WeeklyWeatherModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.math.roundToInt

@Suppress("VarCouldBeVal")
class MapWeeklyWeatherToUiUseCaseTest {

    @MockK
    private lateinit var convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase

    @MockK
    private lateinit var formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase

    @MockK
    private lateinit var convertUnixTimeUseCase: ConvertUnixTimeUseCase

    @MockK
    private lateinit var defineCorrectUnits: DefineCorrectUnitsUseCase

    private lateinit var mapWeeklyWeatherToUiUseCase: MapWeeklyWeatherToUiUseCase

    private lateinit var testWeatherUnits: WeatherUnits

    private lateinit var testWeeklyWeatherDomainUI: WeeklyWeatherDomainUI

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapWeeklyWeatherToUiUseCase = MapWeeklyWeatherToUiUseCase(
            convertWeatherCodeToEnumUseCase,
            formatWindDirectionUseCase,
            convertUnixTimeUseCase,
            defineCorrectUnits
        )
        testWeatherUnits = createTestsUnits()
        testWeeklyWeatherDomainUI = createTestWeeklyWeatherDomainUI()
    }

    @Test
    fun `should correctly map UiSunCyclesModel`() = runTest {
        val testDate = "testDate"
        val selectedIndex = 0
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_MODERATE_53
        val expectedWindDirection = WindDirection.EAST

        val expectedSunCyclesCardModel = UiSuncyclesModel(
            sunriseTime = StringUnit(testDate),
            sunsetTime = StringUnit(testDate)
        )

        coEvery { defineCorrectUnits.defineCorrectUnits() } returns testWeatherUnits
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        every { formatWindDirectionUseCase(any()) } returns expectedWindDirection
        every { convertUnixTimeUseCase.execute(any(), any()) } returns testDate

        val result = mapWeeklyWeatherToUiUseCase(testWeeklyWeatherDomainUI, selectedIndex)

        val actualSunCycles =
            result.mainWeatherRecyclerItems.find { it is UiSuncyclesModel } as UiSuncyclesModel
        assertEquals(expectedSunCyclesCardModel, actualSunCycles)
    }

    @Test
    fun `should correctly map UiOtherPropsModel`() = runTest {
        val testDate = "testDate"
        val selectedIndex = 0
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_MODERATE_53
        val expectedWindDirection = WindDirection.EAST

        val expectedOtherPropsCardModel = UiOtherPropsModel(
            dayLightDuration = SecondsDuration(
                hour = WeatherValueWithUnit(10.0 / 3600, WeatherUnit.HOUR),
                minute = WeatherValueWithUnit((10.0 % 3600) / 60, WeatherUnit.MINUTE)
            ),
            sunShineDuration = SecondsDuration(
                hour = WeatherValueWithUnit((10.0 / 3600.0), WeatherUnit.HOUR),
                minute = WeatherValueWithUnit((10.0 % 3600) / 60, WeatherUnit.MINUTE)
            ),
            maxUvIndex = 6.0.roundToInt().toString()
        )
        coEvery { defineCorrectUnits.defineCorrectUnits() } returns testWeatherUnits
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        every { formatWindDirectionUseCase(any()) } returns expectedWindDirection
        every { convertUnixTimeUseCase.execute(any(), any()) } returns testDate

        val result = mapWeeklyWeatherToUiUseCase(testWeeklyWeatherDomainUI, selectedIndex)
        val actualProps =
            result.mainWeatherRecyclerItems.find { it is UiOtherPropsModel } as UiOtherPropsModel
        assertEquals(expectedOtherPropsCardModel, actualProps)
    }

    @Test
    fun `should correctly map UIPrecipitationCardModel`() = runTest {
        val testDate = "testDate"
        val selectedIndex = 0
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_MODERATE_53
        val expectedWindDirection = WindDirection.EAST

        val expectedPrecipitationCardModel =
            UIPrecipitationCardModel(
                total = WeatherValueWithUnit(
                    10.0,
                    testWeatherUnits.precipitation
                ),
                rainSum = WeatherValueWithUnit(
                    10.0,
                    testWeatherUnits.precipitation
                ),
                showersSum =
                WeatherValueWithUnit(
                    10.0,
                    testWeatherUnits.precipitation
                ),
                snowSum = WeatherValueWithUnit(
                    10.0,
                    if (testWeatherUnits.precipitation == WeatherUnit.MM) WeatherUnit.CM else WeatherUnit.INCH
                ),
                precipitationProbability = WeatherValueWithUnit(
                    1.0,
                    WeatherUnit.PERCENT
                )
            )
        coEvery { defineCorrectUnits.defineCorrectUnits() } returns testWeatherUnits
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        every { formatWindDirectionUseCase(any()) } returns expectedWindDirection
        every { convertUnixTimeUseCase.execute(any(), any()) } returns testDate

        val result = mapWeeklyWeatherToUiUseCase(testWeeklyWeatherDomainUI, selectedIndex)
        val actualPrecipitationModel =
            result.mainWeatherRecyclerItems.find { it is UIPrecipitationCardModel } as UIPrecipitationCardModel
        assertEquals(expectedPrecipitationCardModel, actualPrecipitationModel)
    }

    @Test
    fun `should correctly map UiCardInfoModel`() = runTest {
        val testDate = "testDate"
        val selectedIndex = 0
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_MODERATE_53
        val expectedWindDirection = WindDirection.EAST

        val expectedCardInfoModel = UiCardInfoModel(
            tempMax = WeatherValueWithUnit(
                10.0,
                testWeatherUnits.temperature
            ),
            tempMin = 10.0.toInt().toString(),
            feelsLikeMax = WeatherValueWithUnit(
                22.0,
                testWeatherUnits.temperature
            ),
            feelsLikeMin = WeatherValueWithUnit(
                13.0,
                testWeatherUnits.temperature
            ),
            weatherCondition = expectedWeatherStatus,
            isDay = true
        )

        coEvery { defineCorrectUnits.defineCorrectUnits() } returns testWeatherUnits
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        every { formatWindDirectionUseCase(any()) } returns expectedWindDirection
        every { convertUnixTimeUseCase.execute(any(), any()) } returns testDate

        val result = mapWeeklyWeatherToUiUseCase(testWeeklyWeatherDomainUI, selectedIndex)
        val actualUiCardInfoModel =
            result.mainWeatherRecyclerItems.find { it is UiCardInfoModel } as UiCardInfoModel
        assertEquals(expectedCardInfoModel, actualUiCardInfoModel)
    }

    @Test
    fun `should correctly map UIWindCardModel`() = runTest {
        val testDate = "testDate"
        val selectedIndex = 0
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_MODERATE_53
        val expectedWindDirection = WindDirection.EAST

        val expectedWindCardModel = UIWindCardModel(
            windSpeed = WeatherValueWithUnit(5.0, testWeatherUnits.windSpeed),
            windGust = WeatherValueWithUnit(8.0, testWeatherUnits.windSpeed),
            windDirection = expectedWindDirection,
        )

        coEvery { defineCorrectUnits.defineCorrectUnits() } returns testWeatherUnits
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        every { formatWindDirectionUseCase(any()) } returns expectedWindDirection
        every { convertUnixTimeUseCase.execute(any(), any()) } returns testDate

        val result = mapWeeklyWeatherToUiUseCase(testWeeklyWeatherDomainUI, selectedIndex)
        val actualUIWindCardModel =
            result.mainWeatherRecyclerItems.find { it is UIWindCardModel } as UIWindCardModel
        assertEquals(expectedWindCardModel, actualUIWindCardModel)
    }

    @Test
    fun `should correctly map all`() = runTest {
        val selectedIndex = 0
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_MODERATE_53
        val expectedWindDirection = WindDirection.EAST
        val testDate = "testDate"

        val expectedWindCardModel = UIWindCardModel(
            windSpeed = WeatherValueWithUnit(5.0, testWeatherUnits.windSpeed),
            windGust = WeatherValueWithUnit(8.0, testWeatherUnits.windSpeed),
            windDirection = expectedWindDirection,
        )

        val expectedSunCyclesCardModel = UiSuncyclesModel(
            sunsetTime = StringUnit(testDate),
            sunriseTime = StringUnit(testDate)
        )

        val expectedOtherPropsCardModel = UiOtherPropsModel(
            dayLightDuration = SecondsDuration(
                hour = WeatherValueWithUnit(10.0 / 3600, WeatherUnit.HOUR),
                minute = WeatherValueWithUnit((10.0 % 3600) / 60, WeatherUnit.MINUTE)
            ),
            sunShineDuration = SecondsDuration(
                hour = WeatherValueWithUnit((10.0 / 3600.0), WeatherUnit.HOUR),
                minute = WeatherValueWithUnit((10.0 % 3600) / 60, WeatherUnit.MINUTE)
            ),
            maxUvIndex = 6.0.roundToInt().toString()
        )

        val expectedCardInfoModel = UiCardInfoModel(
            tempMax = WeatherValueWithUnit(
                10.0,
                testWeatherUnits.temperature
            ),
            tempMin = 10.0.toInt().toString(),
            feelsLikeMax = WeatherValueWithUnit(
                22.0,
                testWeatherUnits.temperature
            ),
            feelsLikeMin = WeatherValueWithUnit(
                13.0,
                testWeatherUnits.temperature
            ),
            weatherCondition = expectedWeatherStatus,
            isDay = true
        )

        val expectedPrecipitationCardModel =
            UIPrecipitationCardModel(
                total = WeatherValueWithUnit(
                    10.0,
                    testWeatherUnits.precipitation
                ),
                rainSum = WeatherValueWithUnit(
                    10.0,
                    testWeatherUnits.precipitation
                ),
                showersSum =
                WeatherValueWithUnit(
                    10.0,
                    testWeatherUnits.precipitation
                ),
                snowSum = WeatherValueWithUnit(
                    10.0,
                    if (testWeatherUnits.precipitation == WeatherUnit.MM) WeatherUnit.CM else WeatherUnit.INCH
                ),
                precipitationProbability = WeatherValueWithUnit(
                    1.0,
                    WeatherUnit.PERCENT
                )
            )

        val expectedMainWeatherUiModel = MainWeatherUiModel(
            mainWeatherRecyclerItems = listOf(
                expectedCardInfoModel,
                expectedSunCyclesCardModel,
                expectedOtherPropsCardModel,
                expectedPrecipitationCardModel,
                expectedWindCardModel
            )
        )

        coEvery { defineCorrectUnits.defineCorrectUnits() } returns testWeatherUnits
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        every { formatWindDirectionUseCase(any()) } returns expectedWindDirection
        every { convertUnixTimeUseCase.execute(any(), any()) } returns testDate

        val result: MainWeatherUiModel =
            mapWeeklyWeatherToUiUseCase(testWeeklyWeatherDomainUI, selectedIndex)

        assertEquals(expectedMainWeatherUiModel, result)
    }

    private object TestData {
        fun createTestsUnits() = WeatherUnits(
            temperature = WeatherUnit.FAHRENHEIT,
            precipitation = WeatherUnit.INCH,
            windSpeed = WeatherUnit.MPH
        )

        private val sunCyclesTest = SunCyclesModel(
            sunrise = listOf(1L, 2L, 3L),
            sunset = listOf(1L, 2L, 3L)
        )

        private val precipitationTest = PrecipitationModel(
            level = 10.0,
            rain = 10.0,
            showers = 10.0,
            snowfall = 10.0
        )

        private val windTest = WindModel(
            direction = 1,
            speed = 5.0,
            gust = 8.0
        )

        fun createTestWeeklyWeatherDomainUI() = WeeklyWeatherDomainUI(
            isDay = true,
            utcOffsetSeconds = 1L,
            weekly = WeeklyWeatherModel(
                time = listOf(1696118400, 1696204800),
                weatherCode = listOf(1, 2),
                cityName = "City",
                tempMax = listOf(10.0),
                tempMin = listOf(10.0),
                apparentTempMax = listOf(22.0, 27.0),
                apparentTempMin = listOf(13.0, 22.0),
                sunCycles = sunCyclesTest,
                dayLightDuration = listOf(10.0, 10.0),
                sunshineDuration = listOf(10.0, 10.0),
                uvIndexMax = listOf(6.0, 10.0),
                precipitationProbabilityMax = listOf(1.0, 2.0),
                precipitationData = listOf(precipitationTest, precipitationTest),
                windData = listOf(windTest, windTest),
            )
        )
    }
}
