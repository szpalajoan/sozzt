package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.sample.SozztSpecification

class TerrainVisionSpec extends SozztSpecification {

    def setup() {
        loginUser(MONIKA_CONTRACT_INTRODUCER)
    }

    def "should add terrain vision"() {
        when: "$KRYNICA_CONTRACT is completly introduced by $MONIKA_CONTRACT_INTRODUCER"
            addCompletelyIntroduceContract(KRYNICA_CONTRACT, KRYNICA_CONTRACT_SCAN)
        then: "Terrain vision is added"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == NEW_KRYNICA_TERRAIN_VISION
    }


}
