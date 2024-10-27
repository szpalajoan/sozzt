package pl.jkap.sozzt.instant


import java.time.Instant
import java.time.temporal.ChronoUnit

trait InstantSamples {
    Instant NOW = Instant.now()

    Instant TOMORROW = NOW.plus(1, ChronoUnit.DAYS)
}