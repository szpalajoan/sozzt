package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.contract.domain.ContractConfiguration
import pl.jkap.sozzt.contract.domain.ContractFacade
import pl.jkap.sozzt.sample.SampleSpecification
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionConfiguration
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade

abstract class TerrainVisionBaseSpec extends SampleSpecification {
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(instantProvider)
    TerrainVisionFacade terrainVisionFacade = new TerrainVisionConfiguration().terrainVisionFacade(instantProvider)
}
