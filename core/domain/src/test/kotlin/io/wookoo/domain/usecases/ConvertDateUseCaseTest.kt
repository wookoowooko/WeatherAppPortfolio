package io.wookoo.domain.usecases

import junit.framework.TestCase.assertEquals
import org.junit.Before
import org.junit.Test

class ConvertDateUseCaseTest {

    private lateinit var useCase: ConvertDateUseCase

    @Before
    fun setUp() {
        useCase = ConvertDateUseCase()
    }

    @Test
    fun methodShouldReturnCorrectParsedDate() {
        val expected = "Среда, 26 Февр."
        val actual = useCase(1740563100, offset = 0)
        assertEquals(expected, actual)
    }
}
