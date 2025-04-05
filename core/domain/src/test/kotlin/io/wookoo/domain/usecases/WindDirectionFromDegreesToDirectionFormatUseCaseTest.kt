package io.wookoo.domain.usecases

import io.wookoo.models.units.WindDirection
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals

class WindDirectionFromDegreesToDirectionFormatUseCaseTest {

    private lateinit var windDirectionFromDegreesToDirectionFormatUseCase:
        WindDirectionFromDegreesToDirectionFormatUseCase

    @Before
    fun setUp() {
        windDirectionFromDegreesToDirectionFormatUseCase = WindDirectionFromDegreesToDirectionFormatUseCase()
    }

    @Test
    fun shouldCorrectReturnValue() {
        val input = listOf(22, 95, 63, 362)
        val expectedResults = listOf(
            WindDirection.NORTH,
            WindDirection.EAST,
            WindDirection.NORTH_EAST,
            WindDirection.UNDETECTED
        )

        input.forEachIndexed { index, code ->
            assertEquals(expectedResults[index], windDirectionFromDegreesToDirectionFormatUseCase.invoke(code))
        }
    }
}
