package pl.jkap.sozzt.terrainvision

class TerrainVisionSpec extends TerrainVisionBaseSpec {

    def setup() {

    }

    def "should add terrain vision"() {
        given: "There is a $KRYNICA_CONTRACT"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$KRYNICA_CONTRACT is completly introduced by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.finalizeIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is added"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId)
    }
}
