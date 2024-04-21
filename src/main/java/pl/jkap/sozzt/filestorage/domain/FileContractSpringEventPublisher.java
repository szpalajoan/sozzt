package pl.jkap.sozzt.filestorage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent;

@Component
public class FileContractSpringEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void contractScanUploaded(final ContractScanAddedEvent contractScanAddedEvent) {
        applicationEventPublisher.publishEvent(contractScanAddedEvent);
    }
}