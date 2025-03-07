package io.wookoo.domain.usecases

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertUnixDateToDayNameDayNumberUseCaseTest {

    private lateinit var convertDateUseCase: ConvertUnixDateToDayNameDayNumberUseCase

    @Before
    fun setUp() {
        convertDateUseCase = ConvertUnixDateToDayNameDayNumberUseCase()
    }

    @Test
    fun `should convert Unix timestamp to day name and day number`() {
        val timestamp = 1741046400L
        val expected = "Вт" to "4"
        val result = convertDateUseCase(timestamp)
        assertEquals(expected, result)
    }
}
