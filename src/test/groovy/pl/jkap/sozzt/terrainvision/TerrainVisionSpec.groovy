package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.sample.SozztSpecification

class TerrainVisionSpec extends SozztSpecification {

    def setup() {

    }

    def "should add terrain vision"() {
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        and: "There is a $KRYNICA_CONTRACT added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$KRYNICA_CONTRACT is completly introduced by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.finalizeIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is added"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == NEW_KRYNICA_TERRAIN_VISION
    }


}
