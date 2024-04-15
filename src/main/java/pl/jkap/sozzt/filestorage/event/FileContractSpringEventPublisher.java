package pl.jkap.sozzt.filestorage.event;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;

@Component
public class FileContractSpringEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void fileUploaded(final FileUploadedEvent fileUploadedEvent) {
        applicationEventPublisher.publishEvent(fileUploadedEvent);
    }
}