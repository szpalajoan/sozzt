package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;

public class ProjectPurposesMapPreparationEventPublisherStub extends ProjectPurposesMapPreparationEventPublisher {
    public ProjectPurposesMapPreparationEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }
}
