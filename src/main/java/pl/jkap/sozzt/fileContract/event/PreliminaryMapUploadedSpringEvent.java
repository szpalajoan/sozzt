package pl.jkap.sozzt.fileContract.event;


import java.util.UUID;

public class PreliminaryMapUploadedSpringEvent extends FileUploadedSpringEvent {

    public PreliminaryMapUploadedSpringEvent(UUID idContract) {
        super(idContract);
    }

}