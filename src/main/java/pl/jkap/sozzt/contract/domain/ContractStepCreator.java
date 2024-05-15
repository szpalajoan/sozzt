package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;
import pl.jkap.sozzt.terrainvision.dto.AddTerrainVisionDto;

import java.time.Instant;
import java.util.UUID;

class ContractStepCreator {

    private TerrainVisionFacade terrainVisionFacade;


    ContractStep createTerrainVisionStep(UUID contractId, Instant deadLine) {
        terrainVisionFacade.addTerrainVision(AddTerrainVisionDto.builder()
                .terrainVisionId(contractId)
                .deadLine(deadLine)
                .build());
        return new ContractStep(contractId, ContractStepType.TERRAIN_VISION, ContractStepStatus.IN_PROGRESS);
    }
}
