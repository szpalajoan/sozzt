package pl.jkap.sozzt.filestorage.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ContractScanAddedEvent {
    private final UUID contractId;

    public ContractScanAddedEvent(UUID contractId) {
        this.contractId = contractId;
    }
}