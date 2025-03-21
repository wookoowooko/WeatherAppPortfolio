package io.wookoo.domain.usecases

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class ConvertUnixTimeUseCase @Inject constructor() {
    fun executeList(
        input: List<Long>,
        utcOffsetSeconds: Long,
    ): List<String> {
        return input.map { timestamp ->
            val dateTime = Instant
                .fromEpochSeconds(timestamp + utcOffsetSeconds)
                .toLocalDateTime(TimeZone.UTC)
            "%02d:%02d".format(dateTime.hour, dateTime.minute)
        }.sorted()
    }

    fun execute(
        input: Long,
        utcOffsetSeconds: Long,
    ): String {
        val dateTime = Instant.fromEpochSeconds((input + utcOffsetSeconds))
            .toLocalDateTime(TimeZone.UTC)
            .time
        return "%02d:%02d".format(dateTime.hour, dateTime.minute)
    }
}
