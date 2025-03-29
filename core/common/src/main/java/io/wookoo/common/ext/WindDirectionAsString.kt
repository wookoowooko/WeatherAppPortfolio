package io.wookoo.common.ext

import androidx.annotation.StringRes
import io.wookoo.models.units.WindDirection

@StringRes
fun WindDirection.asLocalizedString(): Int {
    return when (this) {
        WindDirection.NORTH -> io.wookoo.androidresources.R.string.north
        WindDirection.NORTH_EAST -> io.wookoo.androidresources.R.string.north_east
        WindDirection.EAST -> io.wookoo.androidresources.R.string.east
        WindDirection.SOUTH_EAST -> io.wookoo.androidresources.R.string.south_east
        WindDirection.SOUTH -> io.wookoo.androidresources.R.string.south
        WindDirection.SOUTH_WEST -> io.wookoo.androidresources.R.string.south_west
        WindDirection.WEST -> io.wookoo.androidresources.R.string.west
        WindDirection.NORTH_WEST -> io.wookoo.androidresources.R.string.north_west
        WindDirection.UNDETECTED -> io.wookoo.androidresources.R.string.undetected
    }
}
