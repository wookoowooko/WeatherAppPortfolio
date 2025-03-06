package io.wookoo.common

import java.util.Locale

fun Number.asIntAsString(): String {
    return String.format(
        Locale.getDefault(),
        "%d",
        this.toInt()
    )
}
