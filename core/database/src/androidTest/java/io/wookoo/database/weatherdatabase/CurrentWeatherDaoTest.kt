package io.wookoo.database.weatherdatabase

import io.wookoo.database.weatherdatabase.TestData.currentWeather
import io.wookoo.database.weatherdatabase.TestData.dailyWeather
import io.wookoo.database.weatherdatabase.TestData.geoEntity
import io.wookoo.database.weatherdatabase.TestData.hourlyWeather
import io.wookoo.database.weatherdatabase.TestData.testWeatherWithDetails
import junit.framework.TestCase.assertNull
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

internal class CurrentWeatherDaoTest : WeatherDataBaseTest() {

    @Test
    fun insertAndGetCurrentForecast() = runTest {
        currentWeatherDao.insertCurrentForecastWithDetails(
            geoEntity,
            currentWeather,
            hourlyWeather,
            dailyWeather
        )

        val result = currentWeatherDao.getCurrentForecast(1L).first()
        assertEquals(testWeatherWithDetails, result)
    }

    @Test
    fun setSelectedCurrentForecastLocationAsDefault() = runTest {
        currentWeatherDao.insertCurrentForecastWithDetails(
            geoEntity.copy(isCurrent = false),
            currentWeather,
            hourlyWeather,
            dailyWeather
        )
        currentWeatherDao.updateCurrentForecastLocation(1L)
        val result = currentWeatherDao.getCurrentForecast(1L).first()
        assertEquals(testWeatherWithDetails.copy(geoEntity.copy(isCurrent = true)), result)
    }

    @Test
    fun clearOtherCurrentForecastLocations() = runTest {
        val list = listOf(1L, 2L, 3L)
        list.forEach {
            currentWeatherDao.insertCurrentForecastWithDetails(
                geoEntity.copy(isCurrent = true, geoItemId = it),
                currentWeather.copy(id = it),
                hourlyWeather.copy(id = it),
                dailyWeather.copy(id = it)
            )
        }
        currentWeatherDao.clearOtherCurrentForecastLocations(1L)
        val allItems = currentWeatherDao.getAllCurrentForecastLocations().first()

        assertTrue(allItems.find { it.geo.geoItemId == 1L }?.geo?.isCurrent == true)
        assertTrue(allItems.find { it.geo.geoItemId == 2L }?.geo?.isCurrent == false)
        assertTrue(allItems.find { it.geo.geoItemId == 3L }?.geo?.isCurrent == false)
    }

    @Test
    fun getCurrentForecastGeoItemIds() = runTest {
        val list = listOf(1L, 2L, 3L)
        list.forEach {
            currentWeatherDao.insertCurrentForecastWithDetails(
                geoEntity.copy(isCurrent = true, geoItemId = it),
                currentWeather.copy(id = it),
                hourlyWeather.copy(id = it),
                dailyWeather.copy(id = it)
            )
        }
        val result = currentWeatherDao.getCurrentForecastGeoItemIds().first()
        assertEquals(list, result)
    }

    @Test
    fun deleteCurrentForecastEntryByGeoIdCascade() = runTest {
        currentWeatherDao.insertCurrentForecastWithDetails(
            geoEntity.copy(geoItemId = 2L),
            currentWeather,
            hourlyWeather,
            dailyWeather
        )
        assertNotNull(currentWeatherDao.getCurrentForecast(2L).first())

        currentWeatherDao.deleteCurrentForecastEntryByGeoId(2L)

        assertNull(currentWeatherDao.getCurrentForecast(2L).first())
    }

    @Test
    fun testGetAllCurrentForecastLocationsSorted() = runTest {
        val location1 = geoEntity.copy(geoItemId = 1L, cityName = "Moscow", isCurrent = false)

        // must be 1st
        val location2 = geoEntity.copy(
            geoItemId = 2L,
            cityName = "Berlin",
            isCurrent = true
        )
        val location3 = geoEntity.copy(geoItemId = 3L, cityName = "Amsterdam", isCurrent = false)

        currentWeatherDao.insertCurrentForecastWithDetails(
            location1,
            currentWeather.copy(id = 1L),
            hourlyWeather.copy(id = 1L),
            dailyWeather.copy(id = 1L)
        )
        currentWeatherDao.insertCurrentForecastWithDetails(
            location2,
            currentWeather.copy(id = 2L),
            hourlyWeather.copy(id = 2L),
            dailyWeather.copy(id = 2L)
        )
        currentWeatherDao.insertCurrentForecastWithDetails(
            location3,
            currentWeather.copy(id = 3L),
            hourlyWeather.copy(id = 3L),
            dailyWeather.copy(id = 3L)
        )

        val allItems = currentWeatherDao.getAllCurrentForecastLocations().first()

        assertEquals(3, allItems.size)
        assertEquals(2L, allItems[0].geo.geoItemId)
        assertEquals(3L, allItems[1].geo.geoItemId)
        assertEquals(1L, allItems[2].geo.geoItemId)
    }
}
