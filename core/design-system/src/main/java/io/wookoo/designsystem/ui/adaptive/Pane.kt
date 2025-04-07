package io.wookoo.designsystem.ui.adaptive

import android.content.res.Configuration.ORIENTATION_LANDSCAPE
import androidx.compose.material3.adaptive.currentWindowAdaptiveInfo
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
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

enum class Orientation { Portrait, Landscape }

sealed interface Device : Pane {
    data object Smartphone : Device
    data class Tablet(val orientation: Orientation) : Device
}

@Composable
fun isCompactDevice(): Boolean {
    val windowSizeClass = currentWindowAdaptiveInfo().windowSizeClass
    return remember(windowSizeClass) {
        windowSizeClass.windowWidthSizeClass == WindowWidthSizeClass.COMPACT ||
            windowSizeClass.windowHeightSizeClass == WindowHeightSizeClass.COMPACT
    }
}

@Composable
fun rememberPane(): Device {
    val configuration = LocalConfiguration.current
    val isCompact = isCompactDevice()

    return remember(isCompact, configuration.orientation) {
        if (isCompact) {
            Device.Smartphone
        } else {
            val orientation = when (configuration.orientation) {
                ORIENTATION_LANDSCAPE -> Orientation.Landscape
                else -> Orientation.Portrait
            }
            Device.Tablet(orientation)
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
