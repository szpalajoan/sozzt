package pl.jkap.sozzt.documentation.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;

public class DocumentationEventPublisherStub extends DocumentationEventPublisher {
    public DocumentationEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }
}
