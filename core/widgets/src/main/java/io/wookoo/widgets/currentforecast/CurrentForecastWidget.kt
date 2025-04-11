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

package io.wookoo.widgets.currentforecast

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import io.wookoo.models.widgets.CurrentForecastWidgetModel
import java.io.File

class CurrentForecastWidget : GlanceAppWidget() {

    @Suppress("UNCHECKED_CAST")
    override val stateDefinition: GlanceStateDefinition<CurrentForecastWidgetModel>
        get() = object : GlanceStateDefinition<CurrentForecastWidgetModel> {
            override suspend fun getDataStore(
                context: Context,
                fileKey: String,
            ): DataStore<CurrentForecastWidgetModel> {
                return CurrentForecastGlanceWidgetRepo.get(context) as DataStore<CurrentForecastWidgetModel>
            }

            override fun getLocation(context: Context, fileKey: String): File {
                throw NotImplementedError("Not implemented for CurrentForecast Widget State Definition")
            }
        }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val activity = CurrentForecastGlanceWidgetRepo.getTargetActivity(context.applicationContext)

        provideContent {
            GlanceTheme {
                Content(currentState(), activity)
            }
        }
    }
}
