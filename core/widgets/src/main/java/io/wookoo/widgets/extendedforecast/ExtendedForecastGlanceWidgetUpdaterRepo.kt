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
package io.wookoo.widgets.extendedforecast

import android.content.Context
import androidx.datastore.core.DataStore
import dagger.hilt.android.qualifiers.ApplicationContext
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IGlanceWidgetUpdater
import io.wookoo.domain.repo.IWeeklyForecastRepo
import io.wookoo.domain.usecases.ConvertUnixTimeUseCase
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.DefineCorrectUnitsUseCase
import io.wookoo.domain.utils.defineIsNow
import io.wookoo.models.weather.current.additional.HourlyModel
import io.wookoo.models.widgets.CurrentForecastWidgetModel
import io.wookoo.models.widgets.ExtendedCurrentForecastWidgetModel
import io.wookoo.widgets.updateWidget
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class ExtendedForecastGlanceWidgetUpdaterRepo @Inject constructor(
    @ApplicationContext private val appContext: Context,
    private val currentForecastRepo: ICurrentForecastRepo,
    private val weeklyForecastRepo: IWeeklyForecastRepo,
    private val defineCorrectUnitsUseCase: DefineCorrectUnitsUseCase,
    private val convertWeatherCodeToEnumUseCase: ConvertWeatherCodeToEnumUseCase,
    private val convertUnixTimeUseCase: ConvertUnixTimeUseCase,

) : IGlanceWidgetUpdater, DataStore<ExtendedCurrentForecastWidgetModel> {
    override suspend fun forecastSynchronized() {
        updateWidget(appContext, ExtendedForecastWidget::class.java)
    }

    override val data: Flow<ExtendedCurrentForecastWidgetModel> = flow {
        val forecast = currentForecastRepo.getAllCurrentForecastLocations().first().first()
        val units = defineCorrectUnitsUseCase.defineCorrectUnits()
        val weatherStatus =
            convertWeatherCodeToEnumUseCase.invoke(forecast.current.weatherStatus)
        val weeklyData = weeklyForecastRepo.getWeeklyForecastByGeoItemId(forecast.geo.geoItemId)
            .first().weekly
        val hourlyForecasts: HourlyModel = forecast.hourly

        emit(
            ExtendedCurrentForecastWidgetModel(
                currentForecastWidgetModel =
                CurrentForecastWidgetModel(
                    city = forecast.geo.cityName,
                    temp = forecast.current.temperature.asLocalizedUnitValueString(
                        units.temperature,
                        appContext
                    ),
                    maxTemp = weeklyData.tempMax.first()
                        .asLocalizedUnitValueString(units.temperature, appContext),
                    minTemp = weeklyData.tempMin.first()
                        .asLocalizedUnitValueString(units.temperature, appContext),
                    weatherImage = weatherStatus.asLocalizedUiWeatherMap(forecast.current.isDay).first,
                    weatherCondition = appContext.getString(
                        weatherStatus.asLocalizedUiWeatherMap(forecast.current.isDay).second
                    )
                ),

                extendedHourlyWidgetModel = hourlyForecasts.time.mapIndexed { index, time ->
                    val hourlyWeatherStatus = convertWeatherCodeToEnumUseCase.invoke(
                        hourlyForecasts.weatherCode[index]
                    )

                    ExtendedCurrentForecastWidgetModel.ExtendedHourlyWidgetModel(
                        time = convertUnixTimeUseCase.execute(
                            time,
                            forecast.utcOffsetSeconds
                        ),
                        isNow = defineIsNow(
                            input = time,
                            offset = forecast.utcOffsetSeconds
                        ),
                        temp = hourlyForecasts.temperature[index]
                            .asLocalizedUnitValueString(units.temperature, appContext),
                        weatherImage = hourlyWeatherStatus
                            .asLocalizedUiWeatherMap(hourlyForecasts.isDay[index])
                            .first
                    )
                }
            )
        )
    }

    override suspend fun updateData(transform: suspend (t: ExtendedCurrentForecastWidgetModel) -> ExtendedCurrentForecastWidgetModel): ExtendedCurrentForecastWidgetModel {
        throw NotImplementedError("Not implemented in Current Data Store")
    }
}
