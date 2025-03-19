package pl.jkap.sozzt.routepreparation.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;

public class RoutePreparationEventPublisherStub extends RoutePreparationEventPublisher {
    public RoutePreparationEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }
}
