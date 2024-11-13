package pl.jkap.sozzt.preliminaryplanning.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.preliminaryplanning.event.PreliminaryPlanCompletedEvent;

@Component
public class PreliminaryPlanEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public PreliminaryPlanEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void preliminaryPlanCompleted(PreliminaryPlanCompletedEvent preliminaryPlanCompletedEvent) {
        applicationEventPublisher.publishEvent(preliminaryPlanCompletedEvent);
    }
}
