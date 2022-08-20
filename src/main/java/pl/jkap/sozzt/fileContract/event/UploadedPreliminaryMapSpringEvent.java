package pl.jkap.sozzt.fileContract.event;


import java.util.UUID;

public class UploadedPreliminaryMapSpringEvent extends FileUploadedSpringEvent {

    public UploadedPreliminaryMapSpringEvent(Object source, UUID idContract) {
        super(source, idContract);
    }

}