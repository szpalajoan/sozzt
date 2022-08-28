package pl.jkap.sozzt.fileContract.event;



import java.util.UUID;


public abstract class FileUploadedSpringEvent {
    private final UUID idContract;

    public FileUploadedSpringEvent(UUID idContract) {
        this.idContract = idContract;
    }

    public UUID getIdContract() {
        return idContract;
    }
}