package pl.jkap.sozzt.file.event;


import java.util.UUID;

public class PreliminaryMapUploadedSpringEventContract extends ContractFileUploadedSpringEvent {

    public PreliminaryMapUploadedSpringEventContract(UUID idContract) {
        super(idContract);
    }
}