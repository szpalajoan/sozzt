package pl.jkap.sozzt.filestorage.event;

import java.util.UUID;

public class ContractScanAddedEvent {
    private final UUID idContract;

    public ContractScanAddedEvent(UUID idContract) {
        this.idContract = idContract;
    }
    public UUID getMessage() {
        return idContract;
    }
}