package pl.jkap.sozzt.config.application;

import lombok.AllArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import pl.jkap.sozzt.fileContract.event.FileUploadedSpringEvent;
import pl.jkap.sozzt.fileContract.event.UploadedPreliminaryMapSpringEvent;
import pl.jkap.sozzt.fileContract.event.UploadedScanFromTauronSpringEvent;

@AllArgsConstructor
public class ContractSpringEventPublisher {

    private final ApplicationEventPublisher applicationEventPublisher;

    private void publishEvent(FileUploadedSpringEvent fileUploadedSpringEvent){
        applicationEventPublisher.publishEvent(fileUploadedSpringEvent);
    }

    public void storeScanFromTauron(final long idContract) {
        publishEvent(new UploadedScanFromTauronSpringEvent(this, idContract));
    }

    public void storePreliminaryMap(long idContract) {
        publishEvent(new UploadedPreliminaryMapSpringEvent(this, idContract));
    }
}
