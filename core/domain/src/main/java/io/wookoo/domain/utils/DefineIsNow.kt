package io.wookoo.domain.utils

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.DurationUnit
import kotlin.time.toDuration

fun defineIsNow(input: Long, offset: Long): Boolean {
    val now = Clock.System.now()
        .plus(offset.toDuration(DurationUnit.SECONDS))
        .toLocalDateTime(TimeZone.UTC)
        .time
        .hour

    val inputTime = Instant.fromEpochSeconds((input + offset))
        .toLocalDateTime(TimeZone.UTC)
        .time
        .hour

    return now == inputTime
}
