package pl.jkap.sozzt.terrainvision

class TerrainVisionSpec extends TerrainVisionBaseSpec {

    def setup() {

    }


    def "should add terrain vision"() {
        given: "There is a $KRYNICA_CONTRACT"
            contractFacade.addContract(KRYNICA_CONTRACT)
        when: "$KRYNICA_CONTRACT is confirmed"
            contractFacade.confirmContract(KRYNICA_CONTRACT.contractId)
    }
}
