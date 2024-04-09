package pl.jkap.sozzt.instant;

import java.time.Clock;
import java.time.Instant;

public class InstantProvider {
    private Clock clock = Clock.systemDefaultZone();

    public Instant now() {
        return Instant.now(clock);
    }

    public void useFixedClock(Instant fixedInstant) {
        clock = Clock.fixed(fixedInstant, clock.getZone());
    }

    public void useSystemDefaultClock() {
        clock = Clock.systemDefaultZone();
    }

}
