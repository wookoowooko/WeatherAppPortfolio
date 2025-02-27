package io.wookoo.domain.usecases

import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import javax.inject.Inject

class ConvertUnixTimeUseCase @Inject constructor() {
    operator fun invoke(input: List<Long>): List<String> {
        return input.map {
            Instant.fromEpochSeconds(it)
                .toLocalDateTime(TimeZone.UTC)
                .hour.toString().padStart(2, '0') + ":" +
                Instant.fromEpochSeconds(it)
                    .toLocalDateTime(TimeZone.UTC)
                    .minute.toString().padStart(2, '0')
        }.sorted()
    }
}
