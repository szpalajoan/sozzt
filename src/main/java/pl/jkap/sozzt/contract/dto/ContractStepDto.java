package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ContractStepDto {

    public enum ContractStepTypeDto {
        PRELIMINARY_PLAN,
        TERRAIN_VISION,
        PROJECT_PURPOSES_MAP_PREPARATION,
        ROUTE_PREPARATION,
        LAND_EXTRACTS,
        CONSENTS_COLLECTION,
        PREPARATION_OF_DOCUMENTATION
    }

    public enum ContractStepStatusDto {
        NOT_ACTIVE,
        ON_HOLD,
        IN_PROGRESS,
        DONE
    }

    private ContractStepTypeDto contractStepType;
    private ContractStepStatusDto contractStepStatus;
    private Instant deadline;
}
