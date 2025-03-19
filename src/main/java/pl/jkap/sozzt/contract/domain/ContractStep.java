package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.contract.dto.ContractStepDto;

import java.io.Serializable;
import java.time.Instant;

@Entity
@Getter
@Builder
@ToString
class ContractStep implements Serializable {

    private final ContractStepType contractStepType;
    private ContractStepStatus contractStepStatus;
    Instant deadline;


    ContractStep( ContractStepType contractStepType, ContractStepStatus contractStepStatus, Instant deadline) {
        this.contractStepType = contractStepType;
        this.contractStepStatus = contractStepStatus;
        this.deadline = deadline;
    }

    void beginStep() {
    contractStepStatus = ContractStepStatus.IN_PROGRESS;
    }

    void completeStep() {
        this.contractStepStatus = ContractStepStatus.DONE;
    }

    boolean isCompleted() {
        return contractStepStatus == ContractStepStatus.NOT_ACTIVE || contractStepStatus == ContractStepStatus.DONE;
    }

    ContractStepDto dto() {
        return ContractStepDto.builder()
                .contractStepType(ContractStepDto.ContractStepTypeDto.valueOf(contractStepType.name()))
                .contractStepStatus(ContractStepDto.ContractStepStatusDto.valueOf(contractStepStatus.name()))
                .deadline(deadline)
                .build();
    }
}
