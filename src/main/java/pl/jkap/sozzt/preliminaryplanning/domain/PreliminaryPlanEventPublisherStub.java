package pl.jkap.sozzt.preliminaryplanning.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;


public class PreliminaryPlanEventPublisherStub extends PreliminaryPlanEventPublisher {

    public PreliminaryPlanEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }

}