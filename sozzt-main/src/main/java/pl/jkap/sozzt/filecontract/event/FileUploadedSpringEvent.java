package pl.jkap.sozzt.filecontract.event;

import java.util.UUID;

public class FileUploadedSpringEvent {
    private final UUID idContract;

    public FileUploadedSpringEvent(UUID idContract) {
        this.idContract = idContract;
    }
    public UUID getMessage() {
        return idContract;
    }
}