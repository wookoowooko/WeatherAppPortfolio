/*
 * Author - Ruslan Gaivoronskii https://github.com/wookoowooko
 * Copyright 2025 The Android Open Source Project
 *
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package io.wookoo.widgets.hilt.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dagger.multibindings.IntoSet
import io.wookoo.domain.repo.IGlanceWidgetUpdater
import io.wookoo.widgets.currentforecast.CurrentForecastGlanceWidgetUpdaterRepo
import io.wookoo.widgets.humidity.HumidityGlanceWidgetUpdaterRepo
import io.wookoo.widgets.suncycle.SunCycleGlanceWidgetUpdaterRepo
import io.wookoo.widgets.uvindex.UvIndexGlanceWidgetUpdaterRepo
import io.wookoo.widgets.wind.WindGlanceWidgetUpdaterRepo

@Module
@InstallIn(SingletonComponent::class)
object CurrentForecastGlanceWidgetModule {

    @Provides
    @IntoSet
    fun provideCurrentForecastGlanceWidgetRepo(
        currentForecastGlanceWidgetRepo: CurrentForecastGlanceWidgetUpdaterRepo,
    ): IGlanceWidgetUpdater {
        return currentForecastGlanceWidgetRepo
    }

    @Provides
    @IntoSet
    fun provideSunCycleGlanceWidgetUpdaterRepo(
        sunCycleGlanceWidgetUpdaterRepo: SunCycleGlanceWidgetUpdaterRepo,
    ): IGlanceWidgetUpdater {
        return sunCycleGlanceWidgetUpdaterRepo
    }

    @Provides
    @IntoSet
    fun provideUvIndexGlanceWidgetUpdaterRepo(
        uvIndexGlanceWidgetUpdaterRepo: UvIndexGlanceWidgetUpdaterRepo,
    ): IGlanceWidgetUpdater {
        return uvIndexGlanceWidgetUpdaterRepo
    }

    @Provides
    @IntoSet
    fun provideHumidityGlanceWidgetUpdaterRepo(
        humidityGlanceWidgetUpdaterRepo: HumidityGlanceWidgetUpdaterRepo,
    ): IGlanceWidgetUpdater {
        return humidityGlanceWidgetUpdaterRepo
    }

    @Provides
    @IntoSet
    fun provideWindGlanceWidgetUpdaterRepo(
        windGlanceWidgetUpdaterRepo: WindGlanceWidgetUpdaterRepo,
    ): IGlanceWidgetUpdater {
        return windGlanceWidgetUpdaterRepo
    }

}
