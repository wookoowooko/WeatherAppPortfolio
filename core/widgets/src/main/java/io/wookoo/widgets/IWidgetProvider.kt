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
package io.wookoo.widgets

import android.app.Activity
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.wookoo.domain.repo.ICurrentForecastRepo
import io.wookoo.domain.repo.IWeeklyForecastRepo
import io.wookoo.domain.usecases.ConvertWeatherCodeToEnumUseCase
import io.wookoo.domain.usecases.DefineCorrectUnitsUseCase

@EntryPoint
@InstallIn(SingletonComponent::class)
interface IWidgetProvider {
    val targetActivity: Class<out Activity>
}

@EntryPoint
@InstallIn(SingletonComponent::class)
interface WidgetDataEntryPoint {
    fun currentForecastRepo(): ICurrentForecastRepo
    fun weeklyForecastRepo(): IWeeklyForecastRepo
    fun defineCorrectUnitsUseCase(): DefineCorrectUnitsUseCase
    fun convertWeatherCodeToEnumUseCase(): ConvertWeatherCodeToEnumUseCase
}
