package pl.jkap.sozzt.config.application;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.file.event.ContractFileUploadedSpringEvent;
import pl.jkap.sozzt.file.event.PreliminaryMapUploadedSpringEventContract;
import pl.jkap.sozzt.file.event.ScanFromTauronUploadedSpringEventContract;

import java.util.UUID;

@AllArgsConstructor
public class ContractFileSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private void publishEvent(ContractFileUploadedSpringEvent contractFileUploadedSpringEvent) {
        applicationEventPublisher.publishEvent(contractFileUploadedSpringEvent);
    }

    public void storeScanFromTauron(final UUID idContract) {
        publishEvent(new ScanFromTauronUploadedSpringEventContract(idContract));
    }

    public void storePreliminaryMap(UUID idContract) {
        publishEvent(new PreliminaryMapUploadedSpringEventContract(idContract));
    }
}
