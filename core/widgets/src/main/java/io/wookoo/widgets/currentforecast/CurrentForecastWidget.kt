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

package io.wookoo.widgets.currentforecast

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.glance.GlanceId
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.currentState
import androidx.glance.state.GlanceStateDefinition
import io.wookoo.domain.repo.IGlanceWidgetUpdater
import io.wookoo.models.widgets.CurrentForecastWidgetModel
import io.wookoo.widgets.hilt.entrypoints.IGlanceWidgetUpdaterEntryPoint
import io.wookoo.widgets.hilt.ext.getFromEntryPoint
import io.wookoo.widgets.hilt.ext.getTargetActivity
import java.io.File

class CurrentForecastWidget : GlanceAppWidget() {

    override val stateDefinition: GlanceStateDefinition<CurrentForecastWidgetModel>
        get() = object : GlanceStateDefinition<CurrentForecastWidgetModel> {
            override suspend fun getDataStore(
                context: Context,
                fileKey: String,
            ): DataStore<CurrentForecastWidgetModel> {
                val glanceWidgetUpdaters: Set<IGlanceWidgetUpdater> =
                    getFromEntryPoint<IGlanceWidgetUpdaterEntryPoint, Set<IGlanceWidgetUpdater>>(
                        context = context,
                    ) { getGlanceWidgetUpdater() }

                val currentForecastUpdater: CurrentForecastGlanceWidgetUpdaterRepo = glanceWidgetUpdaters
                    .filterIsInstance<CurrentForecastGlanceWidgetUpdaterRepo>()
                    .firstOrNull()
                    ?: throw IllegalArgumentException("CurrentForecastGlanceWidgetUpdaterRepo not found")

                return currentForecastUpdater
            }

            override fun getLocation(context: Context, fileKey: String): File {
                throw NotImplementedError("Not implemented for CurrentForecast Widget State Definition")
            }
        }

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val activity = getTargetActivity(context.applicationContext)

        provideContent {
            GlanceTheme {
                CurrentForecastContent(currentState(), activity)
            }
        }
    }
}
