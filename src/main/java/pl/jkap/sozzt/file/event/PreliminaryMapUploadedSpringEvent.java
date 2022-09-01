package pl.jkap.sozzt.file.event;


import java.util.UUID;

public class PreliminaryMapUploadedSpringEvent extends FileUploadedSpringEvent {

    public PreliminaryMapUploadedSpringEvent(UUID idContract) {
        super(idContract);
    }

}