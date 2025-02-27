package io.wookoo.common

import androidx.annotation.StringRes
import io.wookoo.domain.enums.WindDirection

@StringRes
fun WindDirection.asStringRes(): Int {
    return when (this) {
        WindDirection.NORTH -> io.wookoo.design.system.R.string.north
        WindDirection.NORTH_EAST -> io.wookoo.design.system.R.string.north_east
        WindDirection.EAST -> io.wookoo.design.system.R.string.east
        WindDirection.SOUTH_EAST -> io.wookoo.design.system.R.string.south_east
        WindDirection.SOUTH -> io.wookoo.design.system.R.string.south
        WindDirection.SOUTH_WEST -> io.wookoo.design.system.R.string.south_west
        WindDirection.WEST -> io.wookoo.design.system.R.string.west
        WindDirection.NORTH_WEST -> io.wookoo.design.system.R.string.north_west
        WindDirection.UNDETECTED -> io.wookoo.design.system.R.string.undetected
    }
}
