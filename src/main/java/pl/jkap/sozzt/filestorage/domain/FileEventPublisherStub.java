package pl.jkap.sozzt.filestorage.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;


public class FileEventPublisherStub extends FileEventPublisher {

    public FileEventPublisherStub(InMemoryEventInvoker inMemoryEventInvoker) {
        super(inMemoryEventInvoker);
    }

}