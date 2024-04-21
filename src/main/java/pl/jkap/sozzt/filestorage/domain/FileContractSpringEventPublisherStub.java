package pl.jkap.sozzt.filestorage.domain;

import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;
import pl.jkap.sozzt.inmemory.InMemoryEventListenerInvoker;


public class FileContractSpringEventPublisherStub extends FileContractSpringEventPublisher {

    private final InMemoryEventListenerInvoker invoker;

    public FileContractSpringEventPublisherStub(Object... objects) {
        super();
        invoker = new InMemoryEventListenerInvoker(objects);
    }
    @Override
    public void contractScanUploaded(ContractScanAddedEvent contractScanAddedEvent) {
        invoker.invokeEvent(contractScanAddedEvent);
    }

}