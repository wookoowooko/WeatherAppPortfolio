/*
 * Copyright 2025  - Ruslan Gaivoronskii (aka wookoowookoo) https://github.com/wookoowooko
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

package io.wookoo.widgets.humidity

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IGlanceWidgetUpdater
import io.wookoo.models.units.WeatherUnit
import io.wookoo.models.widgets.HumidityWidgetModel
import io.wookoo.widgets.updateWidget
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class HumidityGlanceWidgetUpdaterRepo @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val currentForecastRepo: ICurrentForecastRepo,
) : IGlanceWidgetUpdater, DataStore<HumidityWidgetModel> {

    override suspend fun forecastSynchronized() {
        updateWidget(appContext, HumidityWidget::class.java)
    }

    override val data: Flow<HumidityWidgetModel> = flow {

        val forecast = currentForecastRepo.getAllCurrentForecastLocations().first().first()

        emit(
            HumidityWidgetModel(
                humidity = forecast.current.relativeHumidity.asLocalizedUnitValueString(
                    WeatherUnit.PERCENT,
                    appContext
                ),
                title = appContext.getString(io.wookoo.androidresources.R.string.humidity_prop)
            )
        )
    }

    override suspend fun updateData(transform: suspend (t: HumidityWidgetModel) -> HumidityWidgetModel): HumidityWidgetModel {
        throw NotImplementedError("Not implemented in Current Data Store")
    }
}
