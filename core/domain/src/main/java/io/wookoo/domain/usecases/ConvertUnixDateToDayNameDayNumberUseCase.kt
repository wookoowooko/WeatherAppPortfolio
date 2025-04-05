package io.wookoo.domain.usecases

import io.wookoo.domain.annotations.CoveredByTest
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

typealias DayName = String
typealias DayNumber = String

@CoveredByTest
class ConvertUnixDateToDayNameDayNumberUseCase @Inject constructor() {

    operator fun invoke(stamp: Long): Pair<DayName, DayNumber> = format(stamp)

    private fun format(unix: Long): Pair<DayName, DayNumber> {
        val date = Instant.fromEpochSeconds(unix)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        val dayName = date.dayOfWeek
            .getDisplayName(TextStyle.SHORT, Locale.getDefault())
            .replaceFirstChar { it.uppercase() }

        val dayNumber = date.dayOfMonth.toString()

        return dayName to dayNumber
    }
}
