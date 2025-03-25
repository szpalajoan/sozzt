package pl.jkap.sozzt.landextracts.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.landextracts.event.LandExtractsCompletedEvent;

@Component
public class LandExtractsEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public LandExtractsEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void landExtractsCompleted(LandExtractsCompletedEvent landExtractsCompletedEvent){
        applicationEventPublisher.publishEvent(landExtractsCompletedEvent);
    }

} 