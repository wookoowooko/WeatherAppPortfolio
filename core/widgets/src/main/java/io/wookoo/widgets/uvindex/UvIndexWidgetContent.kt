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

package io.wookoo.widgets.uvindex

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
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.layout.size
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle
import androidx.glance.unit.ColorProvider
import io.wookoo.designsystem.ui.theme.size_100
import io.wookoo.designsystem.ui.theme.small
import io.wookoo.models.widgets.UvIndexWidgetModel
import io.wookoo.widgets.R

@Composable
internal fun UvIndexWidgetContent(
    currentState: UvIndexWidgetModel,
    targetActivity: Class<out Activity>,
) {
    Box(
        modifier = GlanceModifier
            .clickable(actionStartActivity(targetActivity))
            .padding(small)
            .background(ImageProvider(R.drawable.glance_back), ContentScale.Crop),
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalAlignment = Alignment.CenterVertically,
            modifier = GlanceModifier
                .fillMaxSize()
                .padding(small)
        ) {
            Text(
                text = currentState.text,
                modifier = GlanceModifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.titleLarge.fontSize,
                    color = ColorProvider(Color.White),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )
            Image(
                provider = ImageProvider(io.wookoo.design.system.R.drawable.ic_uv_index),
                contentDescription = null,
                modifier = GlanceModifier.size(size_100)
                    .padding(bottom = small)
            )
            Text(
                text = currentState.uvIndex,
                modifier = GlanceModifier.fillMaxWidth(),
                style = TextStyle(
                    fontSize = MaterialTheme.typography.headlineLarge.fontSize,
                    color = ColorProvider(Color.White),
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                ),
                maxLines = 1
            )
        }
    }
}
