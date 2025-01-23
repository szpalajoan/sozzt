package pl.jkap.sozzt.instant

import spock.lang.Shared

import java.time.Instant
import java.time.temporal.ChronoUnit

trait InstantSamples {

    Instant NOW = Instant.now()

    Instant TWO_WEEKS_AGO = NOW.minus(14, ChronoUnit.DAYS)

    Instant WEEK_AGO = NOW.minus(7, ChronoUnit.DAYS)

    Instant YESTERDAY = NOW.minus(1, ChronoUnit.DAYS)

    Instant TOMORROW = NOW.plus(1, ChronoUnit.DAYS)

    Instant WEEK_AHEAD = NOW.plus(7, ChronoUnit.DAYS)

    Instant TWO_WEEKS_AHEAD = NOW.plus(14, ChronoUnit.DAYS)
}