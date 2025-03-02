package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.projectpurposesmappreparation.event.ProjectPurposesMapPreparationCompletedEvent;
import org.springframework.beans.factory.annotation.Autowired;

@Component
public class ProjectPurposesMapPreparationEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public ProjectPurposesMapPreparationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void projectPurposesMapPreparationCompleted(ProjectPurposesMapPreparationCompletedEvent projectPurposesMapPreparationCompletedEvent) {
        applicationEventPublisher.publishEvent(projectPurposesMapPreparationCompletedEvent);
    }
}
