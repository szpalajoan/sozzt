package pl.jkap.sozzt.filestorage.event;

import pl.jkap.sozzt.inmemory.InMemoryEventListenerInvoker;


class FileContractSpringEventPublisherStub extends FileContractSpringEventPublisher {


    @Override
    public void fileUploaded(FileUploadedEvent fileUploadedEvent) {
        InMemoryEventListenerInvoker.invokeEvent(fileUploadedEvent);
    }

}