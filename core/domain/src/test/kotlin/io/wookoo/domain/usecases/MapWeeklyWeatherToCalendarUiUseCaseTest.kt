package io.wookoo.domain.usecases

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.wookoo.domain.usecases.MapWeeklyWeatherToCalendarUiUseCaseTest.TestData.createTestWeeklyWeatherDomainUI
import io.wookoo.models.ui.UiCalendarDayModel
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.weather.current.additional.PrecipitationModel
import io.wookoo.models.weather.current.additional.SunCyclesModel
import io.wookoo.models.weather.current.additional.WindModel
import io.wookoo.models.weather.weekly.WeeklyWeatherDomainUI
import io.wookoo.models.weather.weekly.additional.WeeklyWeatherModel
import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

@Suppress("VarCouldBeVal")
class MapWeeklyWeatherToCalendarUiUseCaseTest {

    @MockK
    private lateinit var convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase

    @MockK
    private lateinit var convertUnixDateToDayNameDayNumberUseCase: ConvertUnixDateToDayNameDayNumberUseCase

    private lateinit var mapWeeklyWeatherToCalendarUiUseCase: MapWeeklyWeatherToCalendarUiUseCase

    private lateinit var testWeeklyWeatherDomainUIData: WeeklyWeatherDomainUI

    @Before
    fun setUp() {
        MockKAnnotations.init(this)

        mapWeeklyWeatherToCalendarUiUseCase = MapWeeklyWeatherToCalendarUiUseCase(
            convertWeatherCodeToEnumUseCase,
            convertUnixDateToDayNameDayNumberUseCase
        )

        testWeeklyWeatherDomainUIData = createTestWeeklyWeatherDomainUI()
    }

    @Test
    fun `should map correctly`() {
        every { convertUnixDateToDayNameDayNumberUseCase(any()) } returnsMany listOf(
            Pair("Mon", "2"),
            Pair("Tue", "3")
        )
        every { convertWeatherCodeToEnumUseCase(any()) } returnsMany listOf(
            WeatherCondition.OVERCAST_3,
            WeatherCondition.CLEAR_SKY_0
        )

        val expectedResult = listOf(
            UiCalendarDayModel(
                dayName = "Mon",
                dayNumber = "2",
                weatherCondition = WeatherCondition.OVERCAST_3,
                isSelected = false,
                isToday = false,
                isDay = true
            ),
            UiCalendarDayModel(
                dayName = "Tue",
                dayNumber = "3",
                weatherCondition = WeatherCondition.CLEAR_SKY_0,
                isSelected = false,
                isToday = false,
                isDay = true
            ),
        )

        val result = mapWeeklyWeatherToCalendarUiUseCase(testWeeklyWeatherDomainUIData)

        assertEquals(expectedResult, result)
    }

    private object TestData {
        fun createTestWeeklyWeatherDomainUI() = WeeklyWeatherDomainUI(
            isDay = true,
            utcOffsetSeconds = 1L,
            weekly = WeeklyWeatherModel(
                time = listOf(1696118400, 1696204800),
                weatherCode = listOf(1, 2),
                cityName = "City",
                tempMax = listOf(20.0, 25.0),
                tempMin = listOf(15.0, 20.0),
                apparentTempMax = listOf(22.0, 27.0),
                apparentTempMin = listOf(18.0, 22.0),
                sunCycles = sunCyclesTest,
                dayLightDuration = listOf(1.0, 2.0),
                sunshineDuration = listOf(1.0, 2.0),
                uvIndexMax = listOf(1.0, 2.0),
                precipitationProbabilityMax = listOf(1, 2),
                precipitationData = listOf(precipitationTest, precipitationTest),
                windData = listOf(windTest, windTest),
            )
        )

        private val sunCyclesTest = SunCyclesModel(
            sunrise = listOf(1L, 2L, 3L),
            sunset = listOf(1L, 2L, 3L)
        )

        private val precipitationTest = PrecipitationModel(
            level = 0.0,
            rain = 0.0,
            showers = 0.0,
            snowfall = 0.0
        )
        private val windTest = WindModel(
            direction = 1,
            speed = 1.0,
            gust = 1.0
        )
    }
}
