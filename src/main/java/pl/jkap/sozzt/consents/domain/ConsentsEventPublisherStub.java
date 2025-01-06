package pl.jkap.sozzt.consents.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionEventPublisher;


public class ConsentsEventPublisherStub extends ConsentsEventPublisher {

    public ConsentsEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }

}