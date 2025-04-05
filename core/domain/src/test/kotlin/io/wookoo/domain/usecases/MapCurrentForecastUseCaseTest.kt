package io.wookoo.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import io.wookoo.domain.usecases.MapCurrentForecastUseCaseTest.TestData.createTestCurrentWeatherDomain
import io.wookoo.domain.usecases.MapCurrentForecastUseCaseTest.TestData.createTestHourlyItem
import io.wookoo.domain.usecases.MapCurrentForecastUseCaseTest.TestData.createTestsUnits
import io.wookoo.models.geocoding.GeocodingDomainUI
import io.wookoo.models.ui.UiCurrentWeatherModel
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherUnits
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.units.WindDirection
import io.wookoo.models.weather.current.CurrentWeatherDomain
import io.wookoo.models.weather.current.additional.CurrentDayModel
import io.wookoo.models.weather.current.additional.DailyModel
import io.wookoo.models.weather.current.additional.HourlyModel
import io.wookoo.models.weather.current.additional.HourlyModelItem
import io.wookoo.models.weather.current.additional.PrecipitationModel
import io.wookoo.models.weather.current.additional.SunCyclesModel
import io.wookoo.models.weather.current.additional.WindModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import kotlin.math.roundToInt

@Suppress("VarCouldBeVal")
class MapCurrentForecastUseCaseTest {

    @MockK
    private lateinit var convertHourlyModelToHourlyListUseCase: ConvertHourlyModelToHourlyListUseCase

    @MockK
    private lateinit var convertDateUseCase: ConvertDateUseCase

    @MockK
    private lateinit var convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase

    @MockK
    private lateinit var convertUnixTimeUseCase: ConvertUnixTimeUseCase

    @MockK
    private lateinit var formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase

    @MockK
    private lateinit var defineCorrectUnits: DefineCorrectUnitsUseCase

    private lateinit var mapCurrentForecastUseCase: MapCurrentForecastUseCase

    private lateinit var testWeatherUnits: WeatherUnits

    private lateinit var testHourlyItem: HourlyModelItem

    private lateinit var testCurrentWeatherDomain: CurrentWeatherDomain

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        mapCurrentForecastUseCase = MapCurrentForecastUseCase(
            convertHourlyModelToHourlyListUseCase,
            convertDateUseCase,
            convertWeatherCodeToEnumUseCase,
            convertUnixTimeUseCase,
            formatWindDirectionUseCase,
            defineCorrectUnits
        )

        testWeatherUnits = createTestsUnits()
        testHourlyItem = createTestHourlyItem()
        testCurrentWeatherDomain = createTestCurrentWeatherDomain()
    }

    @Test
    fun `must map correctly`() = runTest {
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_LIGHT_51
        val expectedDate = "test date"
        val expectedHourlyList = listOf(testHourlyItem)
        val expectedWindDirection = WindDirection.WEST

        coEvery { defineCorrectUnits.defineCorrectUnits() } returns testWeatherUnits
        coEvery { convertHourlyModelToHourlyListUseCase(any(), any()) } returns expectedHourlyList
        coEvery { convertDateUseCase(any(), any()) } returns expectedDate
        coEvery { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        coEvery { convertUnixTimeUseCase.executeList(any(), any()) } returns listOf(expectedDate)
        coEvery { formatWindDirectionUseCase(any()) } returns expectedWindDirection

        val expectedModel = UiCurrentWeatherModel(
            city = testCurrentWeatherDomain.geo.cityName,
            country = testCurrentWeatherDomain.geo.countryName,
            geoNameId = testCurrentWeatherDomain.geo.geoItemId,
            hourlyList = expectedHourlyList,
            date = expectedDate,
            isDay = testCurrentWeatherDomain.current.isDay,
            humidity = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.relativeHumidity,
                unit = WeatherUnit.PERCENT
            ),
            windSpeed = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.wind.speed,
                unit = testWeatherUnits.windSpeed
            ),
            windGust = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.wind.gust,
                unit = testWeatherUnits.windSpeed
            ),
            precipitation = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.precipitation.level,
                unit = testWeatherUnits.precipitation
            ),
            temperature = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.temperature,
                unit = testWeatherUnits.temperature
            ),
            temperatureFeelsLike = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.feelsLike,
                unit = testWeatherUnits.temperature
            ),
            pressureMsl = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.pressureMSL,
                unit = WeatherUnit.PRESSURE
            ),
            uvIndex = testCurrentWeatherDomain.daily.uvIndexMax.first().roundToInt().toString(),
            weatherStatus = expectedWeatherStatus,
            sunriseTime = expectedDate,
            sunsetTime = expectedDate,
            windDirection = expectedWindDirection
        )

        val result = mapCurrentForecastUseCase(testCurrentWeatherDomain)

        assertEquals(expectedModel, result)
    }

    private object TestData {

        fun createTestsUnits() = WeatherUnits(
            temperature = WeatherUnit.FAHRENHEIT,
            precipitation = WeatherUnit.INCH,
            windSpeed = WeatherUnit.MPH
        )

        fun createTestHourlyItem() =
            HourlyModelItem(
                time = "",
                temperature = WeatherValueWithUnit(),
                weatherCode = WeatherCondition.DRIZZLE_LIGHT_51,
                isDay = true,
                isNow = true
            )

        private val hourlyTest = HourlyModel(
            time = listOf(1L, 2L, 3L),
            temperature = listOf(10.0F, 15.0F, 20.0F),
            weatherCode = listOf(100, 200, 300),
            isDay = listOf(true, false, true)
        )

        private val geoTest = GeocodingDomainUI(
            geoItemId = 1L,
            cityName = "City",
            latitude = 1.0,
            longitude = 1.0,
            countryName = "Country",
            urbanArea = ""
        )

        private val windTest = WindModel(
            direction = 1,
            speed = 1.0,
            gust = 1.0
        )
        private val precipitationTest = PrecipitationModel(
            level = 0.0,
            rain = 0.0,
            showers = 0.0,
            snowfall = 0.0
        )
        private val currentTest = CurrentDayModel(
            time = 1L,
            temperature = 10.0,
            relativeHumidity = 1,
            feelsLike = 10.0,
            isDay = true,
            precipitation = precipitationTest,
            wind = windTest,
            cloudCover = 1,
            pressureMSL = 1.0,
            weatherStatus = 1,
        )
        private val sunCyclesTest = SunCyclesModel(
            sunrise = listOf(1L, 2L, 3L),
            sunset = listOf(1L, 2L, 3L)
        )

        private val dailyTest = DailyModel(
            sunCycles = sunCyclesTest,
            uvIndexMax = listOf(1f, 2f, 3f),
        )

        fun createTestCurrentWeatherDomain() = CurrentWeatherDomain(
            isCurrentLocation = true,
            hourly = hourlyTest,
            geo = geoTest,
            current = currentTest,
            utcOffsetSeconds = 3600,
            daily = dailyTest,
            time = 1L
        )
    }
}
