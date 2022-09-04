package pl.jkap.sozzt.config.application;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.consent.event.AllConsentAcceptedSpringEvent;
import pl.jkap.sozzt.consent.event.ConsentRejectSpringEvent;
import pl.jkap.sozzt.consent.event.ConsentSpringEvent;

import java.util.UUID;

@AllArgsConstructor
public class ConsentSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private void publishEvent(ConsentSpringEvent consentSpringEvent) {
        applicationEventPublisher.publishEvent(consentSpringEvent);
    }

    public void consentReject(final UUID idContract) {
        publishEvent(new ConsentRejectSpringEvent(idContract));
    }

    public void allConsentAccepted(UUID idContract) {
        publishEvent(new AllConsentAcceptedSpringEvent(idContract));
    }
}
