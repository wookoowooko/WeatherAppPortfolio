package io.wookoo.designsystem.ui.adaptive

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalConfiguration
import androidx.window.core.layout.WindowHeightSizeClass
import androidx.window.core.layout.WindowWidthSizeClass

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

interface Pane

sealed interface Orientation {
    data object Portrait : Orientation
    data object Landscape : Orientation
}

sealed interface DeviceConfiguration : Pane {
    data class Smartphone(val orientation: Orientation = Orientation.Portrait) : DeviceConfiguration
    data class Tablet(val orientation: Orientation) : DeviceConfiguration
}

@Composable
fun isSmallDevice(): Boolean {
    val adaptiveInfo = currentWindowAdaptiveInfo()
    val widthSizeClass = adaptiveInfo.windowSizeClass.windowWidthSizeClass
    val heightSizeClass = adaptiveInfo.windowSizeClass.windowHeightSizeClass
    return widthSizeClass == WindowWidthSizeClass.COMPACT ||
        heightSizeClass == WindowHeightSizeClass.COMPACT
}

@Composable
fun definePane(): Pane {
    val configuration = LocalConfiguration.current
    return if (isSmallDevice()) {
        DeviceConfiguration.Smartphone()
    } else {
        when (configuration.orientation) {
            ORIENTATION_LANDSCAPE -> DeviceConfiguration.Tablet(orientation = Orientation.Landscape)
            else -> {
                DeviceConfiguration.Tablet(orientation = Orientation.Portrait)
            }
        }
    }
}

/*
*   Smartphone
*
*   pixel 2 (5 inch) orientation portrait -> w:compact h:medium
*   pixel 2 (5 inch) orientation land -> w:medium h:compact
*
*   pixel 5 orientation portrait -> w:compact h:medium
*   pixel 5 orientation land -> w:expanded h:compact
*
*   pixel 7 orientation portrait -> w:compact h:expanded
*   pixel 7 orientation land -> w:expanded h:compact
*
*   pixel 9 pro xl orientation portrait -> w:compact h:expanded
*   pixel 9 pro xl orientation land -> w:expanded h:compact
*
*   fold closed orientation portrait -> w:compact h:medium
*   fold closed orientation land -> w:medium h:compact
*

*
*   Tablet
*
*   pixel tablet 10 portrait -> w:medium h:expanded
*   pixel tablet 10 land -> w:expanded h:medium
*
*   fold open orientation portrait -> w:expanded h:medium
*   fold open orientation land -> w:medium h:medium
*
*   fold hl orientation portrait -> w:medium h:medium
*   fold hl orientation land -> w:expanded h:medium
*   */
