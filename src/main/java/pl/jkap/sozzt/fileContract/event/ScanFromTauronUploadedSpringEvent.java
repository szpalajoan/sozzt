package pl.jkap.sozzt.fileContract.event;


import java.util.UUID;

public class ScanFromTauronUploadedSpringEvent extends FileUploadedSpringEvent {

    public ScanFromTauronUploadedSpringEvent(UUID idContract) {
        super(idContract);
    }

}