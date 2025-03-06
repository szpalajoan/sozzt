package pl.jkap.sozzt.routedrawing.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.projectpurposesmappreparation.event.ProjectPurposesMapPreparationCompletedEvent;
import pl.jkap.sozzt.routedrawing.event.RoutePreparationCompletedEvent;

@Component
public class RoutePreparationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public RoutePreparationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void routePreparationCompleted(RoutePreparationCompletedEvent routePreparationCompletedEvent) {
        applicationEventPublisher.publishEvent(routePreparationCompletedEvent);
    }
}
