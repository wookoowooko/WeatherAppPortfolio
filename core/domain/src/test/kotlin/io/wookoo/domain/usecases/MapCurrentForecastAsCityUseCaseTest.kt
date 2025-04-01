package io.wookoo.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.wookoo.domain.usecases.MapCurrentForecastAsCityUseCaseTest.TestData.createTestCurrentWeatherDomain
import io.wookoo.domain.usecases.MapCurrentForecastAsCityUseCaseTest.TestData.createTestsUnits
import io.wookoo.models.geocoding.GeocodingDomainUI
import io.wookoo.models.ui.UiCity
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherUnits
import io.wookoo.models.units.WeatherValueWithUnit
import io.wookoo.models.weather.current.CurrentWeatherDomain
import io.wookoo.models.weather.current.additional.CurrentDayModel
import io.wookoo.models.weather.current.additional.DailyModel
import io.wookoo.models.weather.current.additional.HourlyModel
import io.wookoo.models.weather.current.additional.PrecipitationModel
import io.wookoo.models.weather.current.additional.SunCyclesModel
import io.wookoo.models.weather.current.additional.WindModel
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

@Suppress("VarCouldBeVal")
class MapCurrentForecastAsCityUseCaseTest {

    @MockK
    private lateinit var convertDateUseCase: ConvertDateUseCase

    @MockK
    private lateinit var convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase

    @MockK
    private lateinit var defineCorrectUnits: DefineCorrectUnitsUseCase

    private lateinit var mapCurrentForecastAsCityUseCase: MapCurrentForecastAsCityUseCase
    private lateinit var testWeatherUnits: WeatherUnits
    private lateinit var testCurrentWeatherDomain: CurrentWeatherDomain

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mapCurrentForecastAsCityUseCase = MapCurrentForecastAsCityUseCase(
            convertDateUseCase,
            convertWeatherCodeToEnumUseCase,
            defineCorrectUnits
        )

        testWeatherUnits = createTestsUnits()
        testCurrentWeatherDomain = createTestCurrentWeatherDomain()
    }

    @Test
    fun `should map correctly`() = runTest {
        val expectedUnits = testWeatherUnits
        val expectedWeatherStatus = WeatherCondition.DRIZZLE_LIGHT_51
        val expectedDate = "test date"

        coEvery { defineCorrectUnits.defineCorrectUnits() } returns expectedUnits
        every { convertWeatherCodeToEnumUseCase(any()) } returns expectedWeatherStatus
        every { convertDateUseCase(any(), any()) } returns expectedDate

        val expectedUiCity = UiCity(
            isCurrentLocation = testCurrentWeatherDomain.isCurrentLocation,
            minTemperature = WeatherValueWithUnit(
                testCurrentWeatherDomain.hourly.temperature.min(),
                expectedUnits.temperature
            ),
            maxTemperature = WeatherValueWithUnit(
                testCurrentWeatherDomain.hourly.temperature.max(),
                expectedUnits.temperature
            ),
            cityName = testCurrentWeatherDomain.geo.cityName,
            countryName = testCurrentWeatherDomain.geo.countryName,
            temperature = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.temperature,
                expectedUnits.temperature
            ),
            temperatureFeelsLike = WeatherValueWithUnit(
                value = testCurrentWeatherDomain.current.feelsLike,
                expectedUnits.temperature
            ),
            isDay = testCurrentWeatherDomain.current.isDay,
            weatherStatus = expectedWeatherStatus,
            geoItemId = testCurrentWeatherDomain.geo.geoItemId,
            date = expectedDate,
        )

        val result = mapCurrentForecastAsCityUseCase(testCurrentWeatherDomain)

        assertEquals(expectedUiCity, result)
    }

    private object TestData {
        fun createTestsUnits() = WeatherUnits(
            temperature = WeatherUnit.FAHRENHEIT,
            precipitation = WeatherUnit.INCH,
            windSpeed = WeatherUnit.MPH
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
