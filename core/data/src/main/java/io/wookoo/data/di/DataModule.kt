package io.wookoo.data.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.data.repo.CurrentForecastImpl
import io.wookoo.data.repo.DataStoreImpl
import io.wookoo.data.repo.GeoRepoImpl
import io.wookoo.data.repo.WeeklyForecastImpl
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IDataStoreRepo
import io.wookoo.domain.repo.IGeoRepo
import io.wookoo.domain.repo.ILocationProvider
import io.wookoo.domain.repo.IWeeklyForecastRepo
import io.wookoo.geolocation.WeatherLocationManager

@Module
@InstallIn(SingletonComponent::class)
interface DataModule {

    @Binds
    fun bindsWeeklyForecastRepo(
        masterRepo: WeeklyForecastImpl,
    ): IWeeklyForecastRepo

    @Binds
    fun bindsDataStoreRepo(
        dataStoreRepo: DataStoreImpl,
    ): IDataStoreRepo

    @Binds
    fun bindsLocationProvider(
        weatherLocationManager: WeatherLocationManager,
    ): ILocationProvider

    @Binds
    fun bindsIGeoRepository(
        geoRepositoryImpl: GeoRepoImpl,
    ): IGeoRepo

    @Binds
    fun bindsCurrentForecastRepository(
        currentForecastImpl: CurrentForecastImpl,
    ): ICurrentForecastRepo
}
