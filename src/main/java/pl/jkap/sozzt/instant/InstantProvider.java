package pl.jkap.sozzt.instant;

import org.springframework.context.annotation.Configuration;

import java.time.Clock;
import java.time.Instant;

@Configuration
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
