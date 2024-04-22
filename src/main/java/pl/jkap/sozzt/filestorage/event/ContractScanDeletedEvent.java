package pl.jkap.sozzt.filestorage.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ContractScanDeletedEvent {
    private UUID contractId;

    public ContractScanDeletedEvent(UUID contractId) {
        this.contractId = contractId;
    }
}
