package io.wookoo.domain.usecases

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

class ConvertDateUseCase @Inject constructor() {

    operator fun invoke(stamp: Long): String = format(stamp)

    private fun format(unix: Long): String {
        val date = Instant.fromEpochSeconds(unix)
            .toLocalDateTime(TimeZone.currentSystemDefault())

        return StringBuilder()
            .append(
                date.dayOfWeek
                    .getDisplayName(TextStyle.FULL, Locale.getDefault())
                    .replaceFirstChar {
                        it.uppercase()
                    }
            )
            .append(", ")
            .append(date.dayOfMonth)
            .append(" ")
            .append(
                date.month.getDisplayName(
                    TextStyle.SHORT,
                    Locale.getDefault()
                ).replaceFirstChar {
                    it.uppercase()
                }
            )
            .toString()
    }
}
