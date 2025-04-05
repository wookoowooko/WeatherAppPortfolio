package io.wookoo.domain.usecases

import io.wookoo.domain.annotations.CoveredByTest
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import java.time.format.TextStyle
import java.util.Locale
import javax.inject.Inject

@CoveredByTest
class ConvertDateUseCase @Inject constructor() {

    operator fun invoke(stamp: Long, offset: Long): String = format(stamp, offset)

    private fun format(unix: Long, offset: Long): String {
        val date = Instant.fromEpochSeconds(unix + offset)
            .toLocalDateTime(TimeZone.UTC)

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
