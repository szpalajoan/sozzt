package pl.jkap.sozzt.config.application;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.file.event.ConsentConfirmationFileSpringEvent;

import java.util.UUID;

@AllArgsConstructor
public class ConsentFileSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private void publishEvent(ConsentConfirmationFileSpringEvent consentConfirmationFileSpringEvent) {
        applicationEventPublisher.publishEvent(consentConfirmationFileSpringEvent);
    }

    public void consentConfirmationFileUpload(final UUID idConsent) {
        publishEvent(new ConsentConfirmationFileSpringEvent(idConsent));
    }

}
