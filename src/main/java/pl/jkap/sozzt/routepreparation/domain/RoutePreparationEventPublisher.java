package pl.jkap.sozzt.routepreparation.domain;

import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.routepreparation.event.RoutePreparationCompletedEvent;

public class RoutePreparationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public RoutePreparationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void routePreparationCompleted(RoutePreparationCompletedEvent routePreparationCompletedEvent) {
        applicationEventPublisher.publishEvent(routePreparationCompletedEvent);
    }
}
