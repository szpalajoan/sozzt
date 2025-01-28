package pl.jkap.sozzt.documentation.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.documentation.event.DocumentationCompletedEvent;

@Component
public class DocumentationEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    public DocumentationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void documentationCompleted(DocumentationCompletedEvent documentationCompletedEvent) {
        applicationEventPublisher.publishEvent(documentationCompletedEvent);
    }
}
