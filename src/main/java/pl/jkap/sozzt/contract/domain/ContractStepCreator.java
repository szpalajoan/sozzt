package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade;
import pl.jkap.sozzt.preliminaryplanning.dto.AddPreliminaryPlanDto;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;
import pl.jkap.sozzt.terrainvision.dto.AddTerrainVisionDto;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Builder
class ContractStepCreator {

    private final TerrainVisionFacade terrainVisionFacade;
    private final PreliminaryPlanFacade preliminaryPlanFacade;


    ContractStep createPreliminaryPlanStep(UUID contractId, Instant contractOrderDate) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(3));
        preliminaryPlanFacade.addPreliminaryPlan(AddPreliminaryPlanDto.builder()
                .preliminaryPlanId(contractId)
                .deadline(deadline)
                .build());
        return new ContractStep(ContractStepType.PRELIMINARY_PLAN, ContractStepStatus.IN_PROGRESS, deadline);
    }

    ContractStep createTerrainVisionStep(UUID contractId, Instant contractOrderDate) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(21));
        terrainVisionFacade.addTerrainVision(AddTerrainVisionDto.builder()
                .terrainVisionId(contractId)
                .deadLine(deadline)
                .build());
        return new ContractStep(ContractStepType.TERRAIN_VISION, ContractStepStatus.ON_HOLD, deadline);
    }

}
