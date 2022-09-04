package pl.jkap.sozzt.file.event;


import java.util.UUID;

public class ScanFromTauronUploadedSpringEventContract extends ContractFileUploadedSpringEvent {

    public ScanFromTauronUploadedSpringEventContract(UUID idContract) {
        super(idContract);
    }

}