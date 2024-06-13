package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto

import java.time.Duration

trait TerrainVisionSample implements ContractSample{

    TerrainVisionDto NEW_KRYNICA_TERRAIN_VISION = TerrainVisionDto.builder()
            .terrainVisionId(KRYNICA_CONTRACT.contractId)
            .deadline(KRYNICA_CONTRACT.contractDetails.orderDate + Duration.ofDays(21))
            .build()

}