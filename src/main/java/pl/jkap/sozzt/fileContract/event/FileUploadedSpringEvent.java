package pl.jkap.sozzt.fileContract.event;

public class FileUploadedSpringEvent {
    private final long idContract;

    public FileUploadedSpringEvent(long idContract) {
        this.idContract = idContract;
    }
    public long getMessage() {
        return idContract;
    }
}