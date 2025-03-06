package io.wookoo.domain.usecases

import io.wookoo.domain.units.WindDirection
import javax.inject.Inject

class WindDirectionFromDegreesToDirectionFormatUseCase @Inject constructor() {

    operator fun invoke(degrees: Int): WindDirection {
        return when (degrees) {
            in 0 until 45 -> WindDirection.NORTH
            in 45 until 90 -> WindDirection.NORTH_EAST
            in 90 until 135 -> WindDirection.EAST
            in 135 until 180 -> WindDirection.SOUTH_EAST
            in 180 until 225 -> WindDirection.SOUTH
            in 225 until 270 -> WindDirection.SOUTH_WEST
            in 270 until 315 -> WindDirection.WEST
            in 315..360 -> WindDirection.NORTH_WEST
            else -> WindDirection.UNDETECTED
        }
    }
}
