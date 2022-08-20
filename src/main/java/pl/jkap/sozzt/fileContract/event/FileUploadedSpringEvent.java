package pl.jkap.sozzt.fileContract.event;


import org.springframework.context.ApplicationEvent;

import java.util.UUID;


public abstract class FileUploadedSpringEvent extends ApplicationEvent {
    private final UUID idContract;

    public FileUploadedSpringEvent(Object source, UUID idContract) {
        super(source);
        this.idContract = idContract;
    }

    public UUID getIdContract() {
        return idContract;
    }
}