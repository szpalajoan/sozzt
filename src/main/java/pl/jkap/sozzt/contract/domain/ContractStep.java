package pl.jkap.sozzt.contract.domain;

import lombok.Builder;

import java.util.UUID;

@Builder
class ContractStep {
    private final UUID contractStepId;
    private final ContractStepType contractStepType;
    private final ContractStepStatus contractStepStatus;


    public ContractStep(UUID contractStepId, ContractStepType contractStepType, ContractStepStatus contractStepStatus) {
        this.contractStepId = contractStepId;
        this.contractStepType = contractStepType;
        this.contractStepStatus = contractStepStatus;
    }
}
