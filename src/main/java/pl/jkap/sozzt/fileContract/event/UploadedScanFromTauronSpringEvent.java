package pl.jkap.sozzt.fileContract.event;


public class UploadedScanFromTauronSpringEvent extends FileUploadedSpringEvent {

    public UploadedScanFromTauronSpringEvent(Object source, long idContract) {
        super(source, idContract);
    }

}