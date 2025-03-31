package io.wookoo.database.weatherdatabase

import io.wookoo.database.weatherdatabase.TestData.currentWeather
import io.wookoo.database.weatherdatabase.TestData.dailyWeather
import io.wookoo.database.weatherdatabase.TestData.geoEntity
import io.wookoo.database.weatherdatabase.TestData.hourlyWeather
import io.wookoo.database.weatherdatabase.TestData.testWeeklyForecastEntity
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertNotNull

internal class DeleteForecastDaoTest : WeatherDataBaseTest() {

    @Test
    fun deleteCurrentForecastEntryByGeoIdCascade() = runTest {
        currentWeatherDao.insertCurrentForecastWithDetails(
            geoEntity.copy(geoItemId = 2L),
            currentWeather,
            hourlyWeather,
            dailyWeather
        )
        assertNotNull(currentWeatherDao.getCurrentForecast(2L).first())

        deleteForecastsDao.deleteCurrentForecastEntryByGeoId(2L)

        assertNull(currentWeatherDao.getCurrentForecast(2L).first())
    }

    @Test
    fun deleteWeeklyForecastByGeoId() = runTest {
        weeklyWeatherDao.insertWeeklyForecast(
            testWeeklyForecastEntity.copy(geoItemId = 2L)
        )

        assertNotNull(weeklyWeatherDao.getWeeklyForecastByGeoItemId(2L).first())

        deleteForecastsDao.deleteWeeklyForecastByGeoId(2L)

        assertNull(weeklyWeatherDao.getWeeklyForecastByGeoItemId(2L).first())
    }
}
