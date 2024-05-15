package pl.jkap.sozzt.instant;

import java.time.Clock;
import java.time.Instant;

public enum TimeProvider {

    CLOCK(Clock.systemUTC()),
    ;

    TimeProvider(Clock clock) {

    }

    public Instant now() {
        return Clock.systemUTC().instant();
    }
}
