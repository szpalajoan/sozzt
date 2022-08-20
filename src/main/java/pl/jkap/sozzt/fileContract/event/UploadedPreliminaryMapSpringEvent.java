package pl.jkap.sozzt.fileContract.event;


public class UploadedPreliminaryMapSpringEvent extends FileUploadedSpringEvent {

    public UploadedPreliminaryMapSpringEvent(Object source, long idContract) {
        super(source, idContract);
    }

}