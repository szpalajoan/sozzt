package pl.jkap.sozzt.filestorage.domain;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.filestorage.event.*;

@Component
public class FileEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    public FileEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }

    public void contractScanUploaded(final ContractScanAddedEvent contractScanAddedEvent) {
        applicationEventPublisher.publishEvent(contractScanAddedEvent);
    }

    public void contractScanDeleted(ContractScanDeletedEvent contractScanDeletedEvent) {
        applicationEventPublisher.publishEvent(contractScanDeletedEvent);
    }

    public void geodeticMapUploaded(GeodeticMapUploadedEvent geodeticMapUploadedEvent) {
        applicationEventPublisher.publishEvent(geodeticMapUploadedEvent);
    }
}