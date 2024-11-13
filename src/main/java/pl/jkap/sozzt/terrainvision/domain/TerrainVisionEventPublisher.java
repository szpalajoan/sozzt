package pl.jkap.sozzt.terrainvision.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.terrainvision.event.TerrainVisionCompletedEvent;

@Component
public class TerrainVisionEventPublisher {
    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public TerrainVisionEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void terrainVisionCompleted(TerrainVisionCompletedEvent terrainVisionCompletedEvent) {
        applicationEventPublisher.publishEvent(terrainVisionCompletedEvent);
    }
}
