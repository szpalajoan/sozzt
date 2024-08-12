package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;

import java.io.Serializable;
import java.util.UUID;

@Entity
@Builder
class ContractStep implements Serializable {
    @Id
    private final UUID contractStepId;
    private final ContractStepType contractStepType;
    private final ContractStepStatus contractStepStatus;


    public ContractStep(UUID contractStepId, ContractStepType contractStepType, ContractStepStatus contractStepStatus) {
        this.contractStepId = contractStepId;
        this.contractStepType = contractStepType;
        this.contractStepStatus = contractStepStatus;
    }
}
