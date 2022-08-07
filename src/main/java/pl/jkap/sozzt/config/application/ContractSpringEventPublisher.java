package pl.jkap.sozzt.config.application;

import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.fileContract.event.FileUploadedSpringEvent;

public class ContractSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final long idContract) {
        System.out.println("Publishing custom event. ");
        FileUploadedSpringEvent fileUploadedSpringEvent = new FileUploadedSpringEvent(this, idContract);
        applicationEventPublisher.publishEvent(fileUploadedSpringEvent);
    }

    public ContractSpringEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
        this.applicationEventPublisher = applicationEventPublisher;
    }
}
