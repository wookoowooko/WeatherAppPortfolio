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
package io.wookoo.widgets.hilt.ext

import android.app.Activity
import android.content.Context
import dagger.hilt.EntryPoints
import io.wookoo.widgets.hilt.entrypoints.IActivityProvider

internal fun getTargetActivity(appContext: Context): Class<out Activity> {
    val activityProvider: IActivityProvider =
        EntryPoints.get(appContext, IActivityProvider::class.java)
    return activityProvider.targetActivity
}
