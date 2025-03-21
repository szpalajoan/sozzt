package pl.jkap.sozzt.consents.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.consents.event.ConsentsCollectionCompletedEvent;

@Component
public class ConsentsEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ConsentsEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void consentsCollectionCompleted(ConsentsCollectionCompletedEvent consentsCollectionCompletedEvent) {
        applicationEventPublisher.publishEvent(consentsCollectionCompletedEvent);
    }
}
