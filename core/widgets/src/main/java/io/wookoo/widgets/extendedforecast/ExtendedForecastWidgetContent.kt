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

package io.wookoo.widgets.extendedforecast

import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.glance.GlanceModifier
import androidx.glance.Image
import androidx.glance.ImageProvider
import androidx.glance.action.actionStartActivity
import androidx.glance.action.clickable
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.Column
import androidx.glance.layout.ContentScale
import androidx.glance.layout.Row
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import io.wookoo.designsystem.ui.theme.large
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.size_20
import io.wookoo.designsystem.ui.theme.size_30
import io.wookoo.designsystem.ui.theme.size_64
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.models.widgets.ExtendedCurrentForecastWidgetModel
import io.wookoo.widgets.R

@Composable
fun ExtendedForecastWidgetContent(
    data: ExtendedCurrentForecastWidgetModel,
    targetActivity: Class<out Activity>,
    now: String,
) {
    Box(
        modifier = GlanceModifier
            .clickable(actionStartActivity(targetActivity))
            .fillMaxSize()
            .padding(small)
            .background(ImageProvider(R.drawable.glance_back), ContentScale.Crop)
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(small)
        ) {
            Row(
                modifier = GlanceModifier.fillMaxWidth()
                    .padding(small)
                    .padding(bottom = large),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Image(
                    modifier = GlanceModifier.size(size_64),
                    provider = ImageProvider(data.currentForecastWidgetModel.weatherImage),
                    contentDescription = null
                )
                Column {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = GlanceModifier.fillMaxWidth(),
                        horizontalAlignment = Alignment.End
                    ) {
                        Image(
                            provider = ImageProvider(R.drawable.baseline_near_me_24),
                            contentDescription = null
                        )
                        Text(
                            text = data.currentForecastWidgetModel.city,
                            modifier = GlanceModifier,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.titleLarge.fontSize,
                                color = ColorProvider(Color.White),
                                fontWeight = FontWeight.Bold
                            ),
                            maxLines = 1
                        )
                    }

                    Text(
                        text = data.currentForecastWidgetModel.weatherCondition,
                        modifier = GlanceModifier.fillMaxWidth(),
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = ColorProvider(Color.White),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.End
                        ),
                        maxLines = 1,
                    )
                }
            }

            Row(
                modifier = GlanceModifier.fillMaxWidth()
                    .padding(small),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Column {
                    Text(
                        text = data.currentForecastWidgetModel.temp,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                            color = ColorProvider(Color.White),
                            fontWeight = FontWeight.Bold
                        ),
                        maxLines = 1
                    )
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Image(
                            modifier = GlanceModifier.size(size_20),
                            provider = ImageProvider(R.drawable.baseline_arrow_downward_24),
                            contentDescription = null
                        )
                        Text(
                            text = data.currentForecastWidgetModel.minTemp,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                color = ColorProvider(Color.White),
                            ),
                            maxLines = 1
                        )

                        Image(
                            modifier = GlanceModifier
                                .padding(start = small).size(size_20),
                            provider = ImageProvider(R.drawable.baseline_arrow_upward_24),
                            contentDescription = null
                        )

                        Text(
                            text = data.currentForecastWidgetModel.maxTemp,
                            style = TextStyle(
                                fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                color = ColorProvider(Color.White),
                            ),
                            maxLines = 1
                        )
                    }
                }

                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = GlanceModifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.End
                ) {
                    val startIndex = data.extendedHourlyWidgetModel.indexOfFirst { it.isNow }
                    val safeStartIndex = if (startIndex != -1) startIndex else 0

                    val sublist = data.extendedHourlyWidgetModel
                        .drop(safeStartIndex)
                        .take(4)
                    sublist.forEach { hourly ->
                        Column(
                            modifier = GlanceModifier.padding(medium),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = hourly.time,
                                modifier = GlanceModifier,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.titleSmall.fontSize,
                                    color = ColorProvider(Color.White),
                                ),
                                maxLines = 1
                            )

                            Text(
                                text = if (hourly.isNow) now else "",
                                modifier = GlanceModifier,
                                style = TextStyle(
                                    fontSize = MaterialTheme.typography.bodySmall.fontSize,
                                    color = ColorProvider(Color.White),
                                ),
                                maxLines = 1
                            )

                            Image(
                                modifier = GlanceModifier.size(size_30),
                                provider = ImageProvider(hourly.weatherImage),
                                contentDescription = null
                            )

                            Text(
                                text = hourly.temp,
                                modifier = GlanceModifier,
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
    }
}
