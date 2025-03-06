package pl.jkap.sozzt.routedrawing.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationEventPublisher;

public class RoutePreparationEventPublisherStub extends RoutePreparationEventPublisher {
    public RoutePreparationEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }
}
