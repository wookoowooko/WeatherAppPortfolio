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
import android.content.Context
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.provideContent
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import dagger.hilt.android.EntryPointAccessors
import io.wookoo.common.ext.asLocalizedUiWeatherMap
import io.wookoo.common.ext.asLocalizedUnitValueString
import io.wookoo.designsystem.ui.theme.size_30
import io.wookoo.designsystem.ui.theme.size_64
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.models.units.WeatherCondition
import io.wookoo.models.units.WeatherUnits
import io.wookoo.models.weather.current.CurrentWeatherDomain
import io.wookoo.models.weather.weekly.additional.WeeklyWeatherModel
import kotlinx.coroutines.flow.first

class CurrentForecastWidget : GlanceAppWidget() {

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        val appContext = context.applicationContext

        val widgetEntry =
            EntryPointAccessors.fromApplication(appContext, WidgetDataEntryPoint::class.java)
        val targetActivity = EntryPointAccessors.fromApplication(
            appContext,
            IWidgetProvider::class.java
        ).targetActivity

        val forecast =
            widgetEntry.currentForecastRepo().getAllCurrentForecastLocations().first().first()
        val units = widgetEntry.defineCorrectUnitsUseCase().defineCorrectUnits()
        val weatherStatus =
            widgetEntry.convertWeatherCodeToEnumUseCase().invoke(forecast.current.weatherStatus)
        val weeklyData =
            widgetEntry.weeklyForecastRepo().getWeeklyForecastByGeoItemId(
                forecast.geo.geoItemId
            ).first().weekly

        provideContent {
            GlanceTheme {
                Content(targetActivity, forecast, context, units, weatherStatus, weeklyData)
            }
        }
    }

    @Suppress("TopLevelComposableFunctions")
    @Composable
    private fun Content(
        activity: Class<out Activity>,
        forecast: CurrentWeatherDomain,
        context: Context,
        units: WeatherUnits,
        status: WeatherCondition,
        weeklyData: WeeklyWeatherModel,
    ) {
        val city = forecast.geo.cityName
        val temp =
            forecast.current.temperature.asLocalizedUnitValueString(units.temperature, context)
        val iconRes = status.asLocalizedUiWeatherMap(forecast.current.isDay).first
        val weatherConditionString =
            context.getString(status.asLocalizedUiWeatherMap(forecast.current.isDay).second)
        val minTemp = weeklyData.tempMin.first().asLocalizedUnitValueString(
            units.temperature,
            context
        )
        val maxTemp = weeklyData.tempMax.first().asLocalizedUnitValueString(
            units.temperature,
            context
        )

        Box(
            modifier = GlanceModifier
                .clickable(actionStartActivity(activity))
                .fillMaxWidth()
                .padding(small)
                .background(ImageProvider(R.drawable.glance_back), ContentScale.Crop)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = GlanceModifier
                    .fillMaxWidth()
                    .padding(small)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Image(
                        provider = ImageProvider(R.drawable.baseline_near_me_24),
                        contentDescription = null
                    )
                    Text(
                        text = city,
                        modifier = GlanceModifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleLarge.fontSize,
                            color = ColorProvider(Color.White),
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                }

                Text(
                    text = temp,
                    modifier = GlanceModifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                        color = ColorProvider(Color.White),
                    ),
                    maxLines = 1
                )

                Image(
                    modifier = GlanceModifier.size(size_64),
                    provider = ImageProvider(iconRes),
                    contentDescription = null
                )

                Text(
                    text = weatherConditionString,
                    modifier = GlanceModifier.fillMaxWidth(),
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.titleSmall.fontSize,
                        color = ColorProvider(Color.White),
                        fontWeight = FontWeight.Bold
                    ),
                    maxLines = 1
                )

                Row(
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Image(
                        modifier = GlanceModifier.size(size_30),
                        provider = ImageProvider(R.drawable.baseline_arrow_downward_24),
                        contentDescription = null
                    )
                    Text(
                        text = minTemp,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = ColorProvider(Color.White),
                        ),
                        maxLines = 1
                    )

                    Image(
                        modifier = GlanceModifier
                            .padding(start = small).size(size_30),
                        provider = ImageProvider(R.drawable.baseline_arrow_upward_24),
                        contentDescription = null
                    )

                    Text(
                        text = maxTemp,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = ColorProvider(Color.White),
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}
