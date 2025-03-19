package pl.jkap.sozzt.routepreparation.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.routepreparation.event.RoutePreparationCompletedEvent;

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
