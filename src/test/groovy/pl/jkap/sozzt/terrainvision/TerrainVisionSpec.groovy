package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.sample.ExpectedStageSample
import pl.jkap.sozzt.sample.SozztSpecification

class TerrainVisionSpec extends SozztSpecification {

    def setup() {
        loginUser(MONIKA_CONTRACT_INTRODUCER)
    }

    def "should add terrain vision"() {
        when: "preliminary plan is completed"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN)
        then: "Terrain vision is started"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == NEW_KRYNICA_TERRAIN_VISION
    }


}
