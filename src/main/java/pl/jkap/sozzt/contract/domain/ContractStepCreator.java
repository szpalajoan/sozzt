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
public class ContractStepCreator {

    private TerrainVisionFacade terrainVisionFacade;
    private PreliminaryPlanFacade preliminaryPlanFacade;


    ContractStep createPreliminaryPlanStep(UUID contractId, Instant contractOrderDate) {
        preliminaryPlanFacade.addPreliminaryPlan(AddPreliminaryPlanDto.builder()
                .preliminaryPlanId(contractId)
                .deadline(contractOrderDate.plus(Duration.ofDays(3)))
                .build());
        return new ContractStep(contractId, ContractStepType.PRELIMINARY_PLAN, ContractStepStatus.IN_PROGRESS);
    }

    ContractStep createTerrainVisionStep(UUID contractId, Instant contractOrderDate) {
        terrainVisionFacade.addTerrainVision(AddTerrainVisionDto.builder()
                .terrainVisionId(contractId)
                .deadLine(contractOrderDate.plus(Duration.ofDays(21)))
                .build());
        return new ContractStep(contractId, ContractStepType.TERRAIN_VISION, ContractStepStatus.ON_HOLD);
    }

}
