package pl.jkap.sozzt.filestorage.event;

import java.util.UUID;

public class FileUploadedEvent {
    private final UUID idContract;

    public FileUploadedEvent(UUID idContract) {
        this.idContract = idContract;
    }
    public UUID getMessage() {
        return idContract;
    }
}