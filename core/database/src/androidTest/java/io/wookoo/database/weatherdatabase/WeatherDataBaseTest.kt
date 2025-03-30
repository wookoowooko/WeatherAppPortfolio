package io.wookoo.database.weatherdatabase

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import io.wookoo.database.daos.CurrentWeatherDao
import io.wookoo.database.daos.WeeklyWeatherDao
import org.junit.After
import org.junit.Before

internal abstract class WeatherDataBaseTest {

    private lateinit var db: WeatherDataBase
    protected lateinit var currentWeatherDao: CurrentWeatherDao
    protected lateinit var weeklyWeatherDao: WeeklyWeatherDao

    @Before
    fun setUp() {
        db = run {
            val context = ApplicationProvider.getApplicationContext<Context>()
            Room.inMemoryDatabaseBuilder(
                context,
                WeatherDataBase::class.java,
            ).build()
        }
        currentWeatherDao = db.currentWeatherDao()
        weeklyWeatherDao = db.weeklyWeatherDao()
    }

    @After
    fun teardown() = db.close()
}
