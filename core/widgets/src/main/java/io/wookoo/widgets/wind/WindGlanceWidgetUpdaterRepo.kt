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
package io.wookoo.widgets.wind

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wookoo.common.ext.asLocalizedString
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IGlanceWidgetUpdater
import io.wookoo.domain.usecases.DefineCorrectUnitsUseCase
import io.wookoo.domain.usecases.WindDirectionFromDegreesToDirectionFormatUseCase
import io.wookoo.models.widgets.WindWidgetModel
import io.wookoo.widgets.updateWidget
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject


class WindGlanceWidgetUpdaterRepo @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val currentForecastRepo: ICurrentForecastRepo,
    private val defineCorrectUnitsUseCase: DefineCorrectUnitsUseCase,
    private val formatWindDirectionUseCase: WindDirectionFromDegreesToDirectionFormatUseCase,
) : IGlanceWidgetUpdater, DataStore<WindWidgetModel> {

    override suspend fun forecastSynchronized() {
        updateWidget(appContext, WindWidget::class.java)
    }

    override val data: Flow<WindWidgetModel> = flow {
        val forecast = currentForecastRepo.getAllCurrentForecastLocations().first().first()
        val units = defineCorrectUnitsUseCase.defineCorrectUnits()

        emit(
            WindWidgetModel(
                windSpeed = forecast.current.wind.speed.asLocalizedUnitValueString(
                    units.windSpeed,
                    appContext
                ),
                windDirection = appContext.getString(
                    formatWindDirectionUseCase(forecast.current.wind.direction)
                        .asLocalizedString()
                ),
                windGust = forecast.current.wind.gust.asLocalizedUnitValueString(
                    units.windSpeed,
                    appContext
                ),
                windSpeedTitle = appContext.getString(io.wookoo.androidresources.R.string.wind_speed_prop),
                windDirectionTitle = appContext.getString(io.wookoo.androidresources.R.string.wind_direction),
                windGustTitle = appContext.getString(io.wookoo.androidresources.R.string.wind_gust_prop),
                widgetTitle = appContext.getString(io.wookoo.androidresources.R.string.wind)
            )
        )
    }

    override suspend fun updateData(transform: suspend (t: WindWidgetModel) -> WindWidgetModel): WindWidgetModel {
        throw NotImplementedError("Not implemented in Current Data Store")
    }
}