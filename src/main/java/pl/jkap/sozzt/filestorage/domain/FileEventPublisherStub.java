package pl.jkap.sozzt.filestorage.domain;

import pl.jkap.sozzt.inmemory.InMemoryEventInvoker;


public class FileEventPublisherStub extends FileEventPublisher {

    public FileEventPublisherStub(Object... facades) {
        super(new InMemoryEventInvoker(facades));
    }

}