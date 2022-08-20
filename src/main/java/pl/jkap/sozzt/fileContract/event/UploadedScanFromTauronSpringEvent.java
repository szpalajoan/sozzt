package pl.jkap.sozzt.fileContract.event;


import java.util.UUID;

public class UploadedScanFromTauronSpringEvent extends FileUploadedSpringEvent {

    public UploadedScanFromTauronSpringEvent(Object source, UUID idContract) {
        super(source, idContract);
    }

}