package pl.jkap.sozzt.routepreparation.domain;

import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.routepreparation.event.RoutePreparationCompletedEvent;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;

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
