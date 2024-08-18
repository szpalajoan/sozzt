package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ContractStepDto {

    public enum ContractStepTypeDto {
        PRELIMINARY_PLAN,
        TERRAIN_VISION
    }

    public enum ContractStepStatusDto {
        ON_HOLD,
        IN_PROGRESS,
        DONE
    }

    private UUID contractStepId;
    private ContractStepTypeDto contractStepType;
    private ContractStepStatusDto contractStepStatus;
    private Instant deadline;
}
