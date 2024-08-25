package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.sample.ExpectedStageSample
import pl.jkap.sozzt.sample.SozztSpecification
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionAccessException
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionNotFoundException

class TerrainVisionSpec extends SozztSpecification {

    def "should add terrain vision"() {
        when: "preliminary plan is completed"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN)
        then: "Terrain vision is started"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == NEW_KRYNICA_TERRAIN_VISION
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_PRELIMINARY_PLAN_KRYNICA_CONTRACT
    }

    def "should confirm that all photos are uploaded "() {
        given: "there is $NEW_KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision has uploaded all photos"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == with(NEW_KRYNICA_TERRAIN_VISION, [allPhotosUploaded : true])
    }

    def "should not allow confirm that all photos are uploaded for not privileged user"() {
        given: "there is $NEW_KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN)
        and: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        when: "$MONIKA_CONTRACT_INTRODUCER tries to confirm that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision has uploaded all photos"
            thrown(TerrainVisionAccessException)
    }

    def "should not confirm that all photos are uploaded for not existing terrain vision stage"() {
        given: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER tries to confirm that all photos are uploaded on not existing terrain vision stage"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        then: "Confirmation of uploaded all photos is not possible"
            thrown(TerrainVisionNotFoundException)
    }

}
