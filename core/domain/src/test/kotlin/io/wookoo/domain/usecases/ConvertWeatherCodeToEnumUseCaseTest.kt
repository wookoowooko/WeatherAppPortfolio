package io.wookoo.domain.usecases

import io.wookoo.models.units.WeatherCondition
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class ConvertWeatherCodeToEnumUseCaseTest {

    private lateinit var convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase

    @Before
    fun setUp() {
        convertWeatherCodeToEnumUseCase = ConvertWeatherCodeToEnumUseCase()
    }

    @Test
    fun shouldReturnCorrectWeatherCode() {
        val input = listOf(0, 48, 63, -1)
        val expectedResults = listOf(
            WeatherCondition.CLEAR_SKY_0,
            WeatherCondition.FOG_45_OR_48,
            WeatherCondition.RAIN_MODERATE_63,
            WeatherCondition.UNKNOWN
        )

        input.forEachIndexed { index, code ->
            assertEquals(expectedResults[index], convertWeatherCodeToEnumUseCase.invoke(code))
        }
    }
}
