package io.wookoo.database.weatherdatabase

import io.wookoo.database.weatherdatabase.TestData.testWeeklyForecastEntity
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

internal class WeeklyForecastDaoTest : WeatherDataBaseTest() {

    @Test
    fun insertAndGetTest() = runTest {
        weeklyWeatherDao.insertWeeklyForecast(
            testWeeklyForecastEntity
        )
        val result = weeklyWeatherDao.getWeeklyForecastByGeoItemId(1L).first()
        assertEquals(result, testWeeklyForecastEntity)
    }

    @Test
    fun getLastUpdateForWeeklyForecastTest() = runTest {
        weeklyWeatherDao.insertWeeklyForecast(
            testWeeklyForecastEntity.copy(lastUpdate = 100L)
        )
        val result = weeklyWeatherDao.getWeeklyForecastByGeoItemId(1L).first()
        assertEquals(result?.lastUpdate, 100L)
    }
}
