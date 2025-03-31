package io.wookoo.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.DataStoreFactory
import androidx.test.core.app.ApplicationProvider
import io.wookoo.models.settings.UserSettingsModel
import io.wookoo.models.units.WeatherUnit
import kotlinx.coroutines.cancel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.rules.TemporaryFolder
import java.io.File

class UserSettingsPreferencesTest {

    @get:Rule
    val tempFolder = TemporaryFolder()

    private lateinit var testScope: TestScope
    private lateinit var context: Context
    private lateinit var preferences: UserSettingsPreferences
    private lateinit var dataStore: DataStore<UserSettings>

    @Before
    fun setUp() {
        val file = tempFolder.newFile("test.pb")
        testScope = TestScope()
        context = ApplicationProvider.getApplicationContext()
        dataStore = DataStoreFactory.create(
            serializer = UserSettingsSerializer(),
            scope = testScope
        ) { file }

        preferences = UserSettingsPreferences(dataStore = dataStore)
    }

    @After
    fun tearDown() {
        File(context.filesDir, "datastore/test.pb").deleteRecursively()
        testScope.cancel()
    }

    @Test
    fun shouldInitializeDefaultValues() = testScope.runTest {
        preferences.setInitialWeatherUnits()

        val result = preferences.userData.first()
        val expected = UserSettingsModel(
            temperatureUnit = WeatherUnit.CELSIUS.apiValue,
            windSpeedUnit = WeatherUnit.KMH.apiValue,
            precipitationUnit = WeatherUnit.MM.apiValue,
            isLocationChoose = false,
        )
        assertEquals(false, result.isLocationChoose)

        assertEquals(expected, result)
    }

    @Test
    fun shouldSaveInitialLocationPicked() = testScope.runTest {
        preferences.saveInitialLocationPicked(true)

        val result = preferences.userData.first()
        assertEquals(true, result.isLocationChoose)
    }

    @Test
    fun shouldUpdateTemperatureUnit() = testScope.runTest {
        preferences.updateTemperatureUnit(WeatherUnit.FAHRENHEIT.apiValue)

        val result = preferences.userData.first()
        assertEquals(WeatherUnit.FAHRENHEIT.apiValue, result.temperatureUnit)
    }

    @Test
    fun shouldUpdateWindSpeedUnit() = testScope.runTest {
        preferences.updateWindSpeedUnit(WeatherUnit.MS.apiValue)

        val result = preferences.userData.first()
        assertEquals(WeatherUnit.MS.apiValue, result.windSpeedUnit)
    }

    @Test
    fun shouldUpdatePrecipitationUnit() = testScope.runTest {
        preferences.updatePrecipitationUnit(WeatherUnit.INCH.apiValue)

        val result = preferences.userData.first()
        assertEquals(WeatherUnit.INCH.apiValue, result.precipitationUnit)
    }

    @Test
    fun shouldUpdateAllWeatherUnits() = testScope.runTest {
        preferences.updateTemperatureUnit(WeatherUnit.FAHRENHEIT.apiValue)
        preferences.updateWindSpeedUnit(WeatherUnit.MS.apiValue)
        preferences.updatePrecipitationUnit(WeatherUnit.INCH.apiValue)

        val result = preferences.userData.first()
        assertEquals(WeatherUnit.FAHRENHEIT.apiValue, result.temperatureUnit)
        assertEquals(WeatherUnit.MS.apiValue, result.windSpeedUnit)
        assertEquals(WeatherUnit.INCH.apiValue, result.precipitationUnit)
    }
}
