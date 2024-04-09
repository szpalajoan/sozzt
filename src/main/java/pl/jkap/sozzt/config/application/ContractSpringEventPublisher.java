package pl.jkap.sozzt.config.application;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import pl.jkap.sozzt.filecontract.event.FileUploadedSpringEvent;

@Component
public class ContractSpringEventPublisher {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;

    public void publishCustomEvent(final long idContract) {
        System.out.println("Publishing custom event. ");
        FileUploadedSpringEvent fileUploadedSpringEvent = new FileUploadedSpringEvent(idContract);
        applicationEventPublisher.publishEvent(fileUploadedSpringEvent);
    }
}