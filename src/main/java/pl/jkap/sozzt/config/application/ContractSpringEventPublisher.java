package pl.jkap.sozzt.config.application;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.file.event.FileUploadedSpringEvent;
import pl.jkap.sozzt.file.event.PreliminaryMapUploadedSpringEvent;
import pl.jkap.sozzt.file.event.ScanFromTauronUploadedSpringEvent;

import java.util.UUID;

@AllArgsConstructor
public class ContractSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private void publishEvent(FileUploadedSpringEvent fileUploadedSpringEvent) {
        applicationEventPublisher.publishEvent(fileUploadedSpringEvent);
    }

    public void storeScanFromTauron(final UUID idContract) {
        publishEvent(new ScanFromTauronUploadedSpringEvent(idContract));
    }

    public void storePreliminaryMap(UUID idContract) {
        publishEvent(new PreliminaryMapUploadedSpringEvent(idContract));
    }
}
