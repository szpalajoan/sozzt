package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.contract.dto.ContractStepDto;

import java.io.Serializable;
import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@ToString
class ContractStep implements Serializable {
    @Id
    private final UUID contractStepId; //TODO ten id jest tu niepotrzebny -> pewnie bez niego nie startuje apka, bo nie wie co to za obiekt a to będzie jsnob
    private final ContractStepType contractStepType;
    private ContractStepStatus contractStepStatus;
    Instant deadline;


    ContractStep(UUID contractStepId, ContractStepType contractStepType, ContractStepStatus contractStepStatus, Instant deadline) {
        this.contractStepId = contractStepId;
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

    ContractStepDto dto() {
        return ContractStepDto.builder()
                .contractStepId(contractStepId)
                .contractStepType(ContractStepDto.ContractStepTypeDto.valueOf(contractStepType.name()))
                .contractStepStatus(ContractStepDto.ContractStepStatusDto.valueOf(contractStepStatus.name()))
                .deadline(deadline)
                .build();
    }


}
