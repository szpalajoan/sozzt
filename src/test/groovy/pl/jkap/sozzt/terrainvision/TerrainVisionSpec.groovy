package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.sample.ExpectedStageSample
import pl.jkap.sozzt.sample.SozztSpecification
import pl.jkap.sozzt.terrainvision.exception.InvalidMapChangeException
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionAccessException
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionNotFoundException

import static pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto.MapChange.*

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

    def "should confirm changes on map"() {
        given: "there is $NEW_KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER confirm changes on map as $mapChange"
            terrainVisionFacade.confirmChangesOnMap(KRYNICA_CONTRACT.contractId, mapChange)
        then: "Terrain vision has confirmed changes on map as $mapChange"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == with(NEW_KRYNICA_TERRAIN_VISION, [mapChange : mapChange])
        where:
            mapChange << [MODIFIED, NOT_NECESSARY]
    }

    def "should not confirm changes on map as none"() {
        given: "there is $NEW_KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER tries to confirm changes on map as none"
            terrainVisionFacade.confirmChangesOnMap(KRYNICA_CONTRACT.contractId, NONE)
        then: "Terrain vision has not confirmed changes on map"
            thrown(InvalidMapChangeException)
    }

    def "should not allow confirm changes on map for not privileged user"() {
        given: "there is $NEW_KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN)
        and: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        when: "$MONIKA_CONTRACT_INTRODUCER tries to confirm changes on map"
            terrainVisionFacade.confirmChangesOnMap(KRYNICA_CONTRACT.contractId, mapChange)
        then: "Confirmation of changes on map is not possible"
            thrown(TerrainVisionAccessException)
        where:
            mapChange << [MODIFIED, NOT_NECESSARY]
    }

    def "should not confirm changes on map for not existing terrain vision stage"() {
        given: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER tries to confirm changes on map on not existing terrain vision stage"
            terrainVisionFacade.confirmChangesOnMap(KRYNICA_CONTRACT.contractId, mapChange)
        then: "Confirmation of changes on map is not possible for not existing terrain vision stage"
            thrown(TerrainVisionNotFoundException)
        where:
            mapChange << [MODIFIED, NOT_NECESSARY]
    }


}
