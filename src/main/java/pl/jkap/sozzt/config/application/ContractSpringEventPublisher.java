package pl.jkap.sozzt.config.application;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.fileContract.event.FileUploadedSpringEvent;

@AllArgsConstructor
public class ContractSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void storeFileEvent(final long idContract) {
        FileUploadedSpringEvent fileUploadedSpringEvent = new FileUploadedSpringEvent(this, idContract);
        applicationEventPublisher.publishEvent(fileUploadedSpringEvent);
    }

}
