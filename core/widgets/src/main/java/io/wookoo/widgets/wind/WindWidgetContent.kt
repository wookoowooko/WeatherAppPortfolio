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
import io.wookoo.designsystem.ui.theme.medium
import io.wookoo.designsystem.ui.theme.size_40
import io.wookoo.designsystem.ui.theme.size_50
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.models.widgets.WindWidgetModel
import io.wookoo.widgets.R

@Composable
internal fun WindWidgetContent(
    currentState: WindWidgetModel,
    targetActivity: Class<out Activity>,
) {
    Box(
        modifier = GlanceModifier
            .clickable(actionStartActivity(targetActivity))
            .padding(small)
            .background(ImageProvider(R.drawable.glance_back), ContentScale.Crop),
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(small)
        ) {

            Text(
                modifier = GlanceModifier.fillMaxWidth().padding(medium),
                text = currentState.widgetTitle,
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = ColorProvider(Color.White),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )

            Row(
                modifier = GlanceModifier.fillMaxWidth()
                    .padding(bottom = small),
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = GlanceModifier.size(size_50)

                ) {
                    Image(
                        provider = ImageProvider(R.drawable.wind_back),
                        contentDescription = null,
                        modifier = GlanceModifier.size(size_50),
                        contentScale = ContentScale.Crop

                    )
                    Image(
                        provider = ImageProvider(io.wookoo.design.system.R.drawable.ic_wind_speed),
                        contentDescription = null,
                        modifier = GlanceModifier.size(size_40)

                    )
                }

                Column {
                    Text(
                        modifier = GlanceModifier.padding(start = medium),
                        text = currentState.windSpeedTitle,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = ColorProvider(Color.White),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1
                    )

                Text(
                    modifier = GlanceModifier.padding(start = medium),
                    text = currentState.windSpeed,
                    style = TextStyle(
                        fontSize = MaterialTheme.typography.bodySmall.fontSize,
                        color = ColorProvider(Color.White),
                        textAlign = TextAlign.Center
                    ),
                    maxLines = 1
                )}
            }

            Row(
                modifier = GlanceModifier.fillMaxWidth()
                    .padding(bottom = small),
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = GlanceModifier.size(size_50)

                ) {
                    Image(
                        provider = ImageProvider(R.drawable.wind_back),
                        contentDescription = null,
                        modifier = GlanceModifier.size(size_50),
                        contentScale = ContentScale.Crop

                    )
                    Image(
                        provider = ImageProvider(io.wookoo.design.system.R.drawable.ic_wind_direction),
                        contentDescription = null,
                        modifier = GlanceModifier.size(size_40)

                    )
                }
                Column {
                    Text(
                        modifier = GlanceModifier.padding(start = medium),
                        text = currentState.windDirectionTitle,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = ColorProvider(Color.White),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1
                    )
                    Text(
                        modifier = GlanceModifier.padding(start = medium),
                        text = currentState.windDirection,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            color = ColorProvider(Color.White),
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1
                    )
                }
            }


            Row(
                modifier = GlanceModifier.fillMaxWidth()
                    .padding(bottom = small),
                horizontalAlignment = Alignment.Start,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = GlanceModifier.size(size_50)
                ) {
                    Image(
                        provider = ImageProvider(R.drawable.wind_back),
                        contentDescription = null,
                        modifier = GlanceModifier.size(size_50),
                        contentScale = ContentScale.Crop

                    )
                    Image(
                        provider = ImageProvider(io.wookoo.design.system.R.drawable.ic_wind_gust),
                        contentDescription = null,
                        modifier = GlanceModifier.size(size_40)
                    )
                }
                Column {
                    Text(
                        modifier = GlanceModifier.padding(start = medium),
                        text = currentState.windGustTitle,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.titleSmall.fontSize,
                            color = ColorProvider(Color.White),
                            fontWeight = FontWeight.Bold,
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1
                    )
                    Text(
                        modifier = GlanceModifier.padding(start = medium),
                        text = currentState.windGust,
                        style = TextStyle(
                            fontSize = MaterialTheme.typography.bodySmall.fontSize,
                            color = ColorProvider(Color.White),
                            textAlign = TextAlign.Center
                        ),
                        maxLines = 1
                    )
                }
            }
        }
    }
}