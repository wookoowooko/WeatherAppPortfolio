package io.wookoo.network

import JvmUnitTestDemoAssetManager
import io.wookoo.domain.utils.AppResult
import io.wookoo.domain.utils.DataError
import io.wookoo.network.demo.DemoForecastImpl
import io.wookoo.network.dto.weather.current.CurrentWeatherDto
import io.wookoo.network.dto.weather.current.CurrentWeatherResponseDto
import io.wookoo.network.dto.weather.current.DailyDto
import io.wookoo.network.dto.weather.current.HourlyDto
import io.wookoo.network.dto.weather.weekly.CurrentWeatherShortDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherDto
import io.wookoo.network.dto.weather.weekly.WeeklyWeatherResponseDto
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class DeSerializationForecastTest {

    private lateinit var subject: DemoForecastImpl

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setUp() {
        subject = DemoForecastImpl(
            ioDispatcher = testDispatcher,
            networkJson = Json { ignoreUnknownKeys = true },
            demoAssetManager = JvmUnitTestDemoAssetManager,
        )
    }

    @Test
    fun testDeserializationCurrentForecast() = runTest(testDispatcher) {

        val expected = CurrentWeatherResponseDto(
            current = CurrentWeatherDto(
                time = 1743669000,
                temperature = 10.4,
                relativeHumidity = 48,
                feelsLike = 7.4,
                isDay = 1,
                precipitation = 0.00,
                rain = 0.00,
                showers = 0.00,
                weatherCode = 0,
                windSpeed = 5.4,
                windDirection = 82,
                windGusts = 15.5,
                cloudCover = 0,
                snowfall = 0.00,
                pressureMSL = 1028.9,
                uvIndex = 3.0,
            ),
            hourly = HourlyDto(
                time = listOf(
                    1743631200,
                    1743634800,
                    1743638400,
                    1743642000,
                    1743645600,
                    1743649200,
                    1743652800,
                    1743656400,
                    1743660000,
                    1743663600,
                    1743667200,
                    1743670800,
                    1743674400,
                    1743678000,
                    1743681600,
                    1743685200,
                    1743688800,
                    1743692400,
                    1743696000,
                    1743699600,
                    1743703200,
                    1743706800,
                    1743710400,
                    1743714000
                ),
                weatherCode = listOf(
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0
                ),
                isDay = listOf(
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    0,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    1,
                    0,
                    0,
                    0,
                    0
                ),
                temperature = listOf(
                    8.9f,
                    8.6f,
                    7.2f,
                    6.9f,
                    6.8f,
                    5.8f,
                    5.5f,
                    5.2f,
                    5.9f,
                    7.3f,
                    9.1f,
                    11.3f,
                    13.5f,
                    15.5f,
                    17.0f,
                    17.9f,
                    18.1f,
                    18.3f,
                    18.1f,
                    17.6f,
                    16.8f,
                    15.2f,
                    14.0f,
                    12.9f
                )
            ),
            daily = DailyDto(
                sunrise = listOf(1743654911),
                sunset = listOf(1743702233)
            ),
            utcOffsetSeconds = 7200
        )

        val actual: AppResult<CurrentWeatherResponseDto, DataError.Remote> =
            subject.getCurrentWeather(
                latitude = anyTestData(),
                longitude = anyTestData(),
                temperatureUnit = anyTestData(),
                windSpeedUnit = anyTestData(),
                precipitationUnit = anyTestData(),
            )

        assertTrue(actual is AppResult.Success)
        assertEquals(expected, (actual as AppResult.Success).data)
    }

    @Test
    fun testDeserializationWeeklyForecast() = runTest(testDispatcher) {
        val expected = WeeklyWeatherResponseDto(
            currentShort = CurrentWeatherShortDto(
              isDay = 1,
            ),
            utcOffsetSeconds = 7200,
            week = WeeklyWeatherDto(
                time = listOf(
                    1743631200,
                    1743717600,
                    1743804000,
                    1743890400,
                    1743976800,
                    1744063200,
                    1744149600
                ),
                weatherCode = listOf(
                    0,
                    3,
                    3,
                    2,
                    3,
                    2,
                    3
                ),
                tempMax = listOf(
                    18.5,
                    20.9,
                    12.1,
                    7.0,
                    12.7,
                    11.9,
                    15.3
                ),
                tempMin = listOf(
                    5.2,
                    8.5,
                    1.9,
                    -1.2,
                    0.5,
                    2.7,
                    0.4
                ),
                apparentTempMax = listOf(  16.0,
                    19.6,
                    9.6,
                    2.6,
                    8.1,
                    8.2,
                    11.4),
                apparentTempMin = listOf(    2.0,
                    6.6,
                    -2.4,
                    -4.9,
                    -3.3,
                    -0.2,
                    -2.6),
                sunrise = listOf(     1743654911,
                    1743741170,
                    1743827430,
                    1743913689,
                    1743999949,
                    1744086210,
                    1744172471),
                sunset = listOf(  1743702233,
                    1743788738,
                    1743875244,
                    1743961749,
                    1744048254,
                    1744134759,
                    1744221265),
                dayLightDuration = listOf(  47310.89,
                    47556.80,
                    47802.66,
                    48048.35,
                    48293.70,
                    48538.57,
                    48782.79),
                sunshineDuration = listOf(  42671.68,
                    42809.69,
                    41794.34,
                    44065.28,
                    43501.73,
                    44453.48,
                    44053.04),
                uvIndexMax = listOf(4.70,
                    4.80,
                    4.85,
                    4.70,
                    4.25,
                    3.00,
                    3.30),
                rainSum = listOf(
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00
                ),
                showersSum = listOf(
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00
                ),
                snowfallSum = listOf(
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00
                ),
                precipitationProbabilityMax = listOf(
                    0,
                    0,
                    0,
                    3,
                    5,
                    2,
                    3
                ),
                windSpeedMax = listOf(
                    10.0,
                    11.4,
                    17.3,
                    10.5,
                    15.0,
                    9.4,
                    13.3
                ),
                windGustsMax = listOf(
                    22.7,
                    24.8,
                    42.5,
                    28.1,
                    37.1,
                    25.2,
                    32.4
                ),
                windDirectionMax = listOf(
                    65,
                    352,
                    25,
                    15,
                    305,
                    38,
                    294
                ),
                precipitationSum = listOf(
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00,
                    0.00
                )
            )
        )

        val actual = subject.getWeeklyWeather(
                latitude = anyTestData(),
                longitude = anyTestData(),
                temperatureUnit = anyTestData(),
                windSpeedUnit = anyTestData(),
                precipitationUnit = anyTestData(),
            )

        assertTrue(actual is AppResult.Success)
        assertEquals(expected, (actual as AppResult.Success).data)

    }


}

