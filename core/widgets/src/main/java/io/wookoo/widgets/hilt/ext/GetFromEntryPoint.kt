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

package io.wookoo.widgets.hilt.ext

import android.content.Context
import dagger.hilt.EntryPoints

inline fun <reified EntryPoint : Any, reified T> getFromEntryPoint(
    context: Context,
    crossinline provider: EntryPoint.() -> T,
): T {
    val entryPoint = EntryPoints.get(context, EntryPoint::class.java)
    return provider(entryPoint)
}
