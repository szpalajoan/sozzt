package pl.jkap.sozzt.instant


import java.time.Instant
import java.time.temporal.ChronoUnit

trait InstantSamples {

    Instant TWO_WEEKS_AGO = Instant.now().minus(14, ChronoUnit.DAYS)

    Instant WEEK_AGO = Instant.now().minus(7, ChronoUnit.DAYS)

    Instant NOW = Instant.now()

    Instant TOMORROW = NOW.plus(1, ChronoUnit.DAYS)

    Instant WEEK_AHEAD = NOW.plus(7, ChronoUnit.DAYS)
}