package pl.jkap.sozzt.terrainvision.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;


public class TerrainVisionEventPublisherStub extends TerrainVisionEventPublisher {

    public TerrainVisionEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }

}