package io.wookoo.designsystem.ui.utils

import kotlinx.datetime.Clock

object SingleClickHandler {
    private var lastClickTime = 0L

    fun singleClick(block: () -> Unit) {
        val currentTime = Clock.System.now().toEpochMilliseconds()
        if (currentTime - lastClickTime < 1000) {
            return
        }
        block()
        lastClickTime = currentTime
    }
}
