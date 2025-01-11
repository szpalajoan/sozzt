package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto

import java.time.Duration

trait TerrainVisionSample implements ContractSample{

    TerrainVisionDto KRYNICA_TERRAIN_VISION = TerrainVisionDto.builder()
            .terrainVisionId(KRYNICA_CONTRACT.contractId)
            .allPhotosUploaded(false)
            .deadline(KRYNICA_CONTRACT.contractDetails.orderDate + Duration.ofDays(21))
            .terrainVisionStatus(TerrainVisionDto.TerrainVisionStatus.IN_PROGRESS)
            .routePreparationNeed(TerrainVisionDto.RoutePreparationNeed.NONE)
            .build()

    TerrainVisionDto COMPLETED_KRYNICA_TERRAIN_VISION = TerrainVisionDto.builder()
            .terrainVisionId(KRYNICA_CONTRACT.contractId)
            .allPhotosUploaded(true)
            .deadline(KRYNICA_CONTRACT.contractDetails.orderDate + Duration.ofDays(21))
            .terrainVisionStatus(TerrainVisionDto.TerrainVisionStatus.COMPLETED)
            .routePreparationNeed(TerrainVisionDto.RoutePreparationNeed.NECESSARY)
            .build()


    TerrainVisionDto with(TerrainVisionDto terrainVisionDto, Map<String, Object> properties) {
        return SampleModifier.with(TerrainVisionDto.class, terrainVisionDto, properties)
    }
}