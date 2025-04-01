package io.wookoo.domain.usecases

import io.mockk.coEvery
import io.mockk.mockk
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.models.settings.UserSettingsModel
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.units.WeatherUnits
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test

class DefineCorrectUnitsUseCaseTest {

    private lateinit var defineCorrectUnitsUseCase: DefineCorrectUnitsUseCase
    private lateinit var dataStoreRepo: IDataStoreRepo

    @Before
    fun setUp() {
        dataStoreRepo = mockk()
        defineCorrectUnitsUseCase = DefineCorrectUnitsUseCase(dataStoreRepo)
    }

    @Test
    fun `should return correct units based on user settings`() = runTest {
        val mockSettings = UserSettingsModel(
            temperatureUnit = WeatherUnit.FAHRENHEIT.apiValue,
            precipitationUnit = WeatherUnit.INCH.apiValue,
            windSpeedUnit = WeatherUnit.KMH.apiValue,
            isLocationChoose = true
        )

        coEvery { dataStoreRepo.userSettings } returns flowOf(mockSettings)

        val result: WeatherUnits = defineCorrectUnitsUseCase.defineCorrectUnits()

        assertEquals(WeatherUnit.FAHRENHEIT, result.temperature)
        assertEquals(WeatherUnit.INCH, result.precipitation)
        assertEquals(WeatherUnit.KMH, result.windSpeed)
    }
}
