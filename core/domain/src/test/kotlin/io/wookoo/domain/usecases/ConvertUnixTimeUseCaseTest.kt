package io.wookoo.domain.usecases

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertUnixTimeUseCaseTest {

    private lateinit var useCase: ConvertUnixTimeUseCase

    @Before
    fun setUp() {
        useCase = ConvertUnixTimeUseCase()
    }

    @Test
    fun `execute should return correct formatted time`() {
        val unixTime = 1700000000L // 14 nov. 2023, 22:13:20
        val utcOffsetSeconds = 3600L // + UTC+1

        val result = useCase.execute(unixTime, utcOffsetSeconds)

        assertEquals("23:13", result)
    }

    @Test
    fun `executeList should return correctly sorted time list`() {
        val timestamps = listOf(1700000000L, 1700003600L, 1700007200L) // 22:13:20, 23:13:20, 00:13:20 UTC
        val utcOffsetSeconds = 3600L // + UTC+1

        val result = useCase.executeList(timestamps, utcOffsetSeconds)

        assertEquals(listOf("23:13", "00:13", "01:13"), result)
    }
}
