package io.wookoo.weatherappportfolio.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.domain.repo.widgets.ICurrentForecastGlanceWidget
import io.wookoo.widgets.currentforecast.CurrentForecastGlanceWidgetRepo

@Module
@InstallIn(SingletonComponent::class)
interface CurrentForecastGlanceWidgetModule {

    @Binds
    fun bindsCurrentForecastGlanceWidgetRepo(
        currentForecastGlanceWidgetRepo: CurrentForecastGlanceWidgetRepo,
    ): ICurrentForecastGlanceWidget
}
