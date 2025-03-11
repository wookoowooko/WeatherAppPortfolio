package io.wookoo.weekly.screen

import android.view.View

data class ViewVisibilityState(
    val loadingIndicator: Int = View.GONE,
    val calendarRecycler: Int = View.GONE,
    val mainRecycler: Int = View.GONE,
)

