package io.wookoo.designsystem.ui

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.VisibilityThreshold
import androidx.compose.animation.core.spring
import androidx.compose.foundation.lazy.LazyItemScope
import androidx.compose.foundation.lazy.grid.LazyGridItemScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.IntOffset

fun Modifier.animateLazyItem(
    lazyListScope: LazyItemScope,
): Modifier {
    with(lazyListScope) {
        return this@animateLazyItem.animateItem(
            fadeInSpec = spring(stiffness = Spring.StiffnessMediumLow),
            placementSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                visibilityThreshold = IntOffset.VisibilityThreshold
            ),
            fadeOutSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )
    }
}

fun Modifier.animateLazyItem(
    lazyGridScope: LazyGridItemScope,
): Modifier {
    with(lazyGridScope) {
        return this@animateLazyItem.animateItem(
            fadeInSpec = spring(stiffness = Spring.StiffnessMediumLow),
            placementSpec = spring(
                stiffness = Spring.StiffnessMediumLow,
                visibilityThreshold = IntOffset.VisibilityThreshold
            ),
            fadeOutSpec = spring(stiffness = Spring.StiffnessMediumLow)
        )
    }
}
