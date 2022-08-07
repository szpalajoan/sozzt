package pl.jkap.sozzt.fileContract.event;


import org.springframework.context.ApplicationEvent;

public class FileUploadedSpringEvent extends ApplicationEvent {
    private final long idContract;

    public FileUploadedSpringEvent(Object source, long idContract) {
        super(source);
        this.idContract = idContract;
    }

    public long getIdContract() {
        return idContract;
    }
}