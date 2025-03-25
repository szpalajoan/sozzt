package pl.jkap.sozzt.landextracts.domain;

import org.springframework.context.ApplicationEventPublisher;

public class LandExtractsEventPublisherStub extends LandExtractsEventPublisher {

    public LandExtractsEventPublisherStub(ApplicationEventPublisher applicationEventPublisher) {
        super(applicationEventPublisher);
    }
}