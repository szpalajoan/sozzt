package pl.jkap.sozzt.terrainvision

import pl.jkap.sozzt.sample.ExpectedStageSample
import pl.jkap.sozzt.sample.SozztSpecification
import pl.jkap.sozzt.terrainvision.exception.CompletionTerrainVisionException
import pl.jkap.sozzt.terrainvision.exception.InvalidProjectPurposesMapPreparationNeedException
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionAccessException
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionNotFoundException

import static pl.jkap.sozzt.terrainvision.domain.ProjectPurposesMapPreparationNeed.*

class TerrainVisionSpec extends SozztSpecification {

    def "should add terrain vision"() {
        when: "preliminary plan is completed"
            addKrynicaContractOnStage(ExpectedStageSample.COMPLETED_PRELIMINARY_PLAN)
        then: "Terrain vision is started"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == KRYNICA_TERRAIN_VISION
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_PRELIMINARY_PLAN_KRYNICA_CONTRACT
    }

    def "should confirm that all photos are uploaded "() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER confirms that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision has uploaded all photos"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == with(KRYNICA_TERRAIN_VISION, [allPhotosUploaded: true])
    }

    def "should not allow confirm that all photos are uploaded for not privileged user"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
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

    def "should confirm project purposes map preparation need"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER sets project purposes map preparation need as $projectPurposesMapPreparationNeed"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, projectPurposesMapPreparationNeed)
        then: "Terrain vision has confirmed project purposes map preparation as $projectPurposesMapPreparationNeed"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == with(KRYNICA_TERRAIN_VISION, [projectPurposesMapPreparationNeed: projectPurposesMapPreparationNeed])
        where:
        projectPurposesMapPreparationNeed << [NECESSARY, NOT_NEED]
    }

    def "should not confirm project purposes map preparation need as none"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER tries to set project purposes map preparation need as none"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, NONE)
        then: "Terrain vision has not confirmed project purposes map preparation need"
            thrown(InvalidProjectPurposesMapPreparationNeedException)
    }

    def "should not allow confirm project purposes map preparation need for not privileged user"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        when: "$MONIKA_CONTRACT_INTRODUCER tries to set project purposes map preparation need"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, mapChange)
        then: "Setting of project purposes map preparation need is not allowed for not privileged user account"
            thrown(TerrainVisionAccessException)
        where:
            mapChange << [NECESSARY, NOT_NEED]
    }

    def "should not confirm project purposes map preparation need for not existing terrain vision stage"() {
        given: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER tries to confirmproject purposes map preparation need on not existing terrain vision stage"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, mapChange)
        then: "Setting of project purposes map preparation need is not possible for not existing terrain vision stage"
            thrown(TerrainVisionNotFoundException)
        where:
            mapChange << [NECESSARY, NOT_NEED]
    }

    def "should complete terrain vision"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        and: "$MARCIN_TERRAIN_VISIONER confirmed that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER sets project purposes map preparation need as $projectPurposesMapPreparationNeed"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, projectPurposesMapPreparationNeed)
        when: "$MARCIN_TERRAIN_VISIONER completes the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is completed"
            terrainVisionFacade.getTerrainVision(KRYNICA_CONTRACT.contractId) == with(COMPLETED_KRYNICA_TERRAIN_VISION, [projectPurposesMapPreparationNeed : projectPurposesMapPreparationNeed])
        where:
            projectPurposesMapPreparationNeed << [NECESSARY, NOT_NEED]
    }

    def "should not complete terrain vision if not all photos are uploaded"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage without all photos uploaded"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER tries to complete the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is not completed"
            thrown(CompletionTerrainVisionException)
    }

    def "should not complete terrain vision if project purposes map preparation need is not specified"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        and: "$MARCIN_TERRAIN_VISIONER confirmed that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        when: "$MARCIN_TERRAIN_VISIONER tries to complete the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is not completed"
            thrown(InvalidProjectPurposesMapPreparationNeedException)
    }


    def "should not complete terrain vision for not existing terrain vision stage"() {
        given: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        when: "$MARCIN_TERRAIN_VISIONER tries to complete not existing terrain vision stage"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is not completed"
            thrown(TerrainVisionNotFoundException)
    }

    def "should not complete terrain vision if not privileged user"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        when: "$MONIKA_CONTRACT_INTRODUCER tries to complete the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is not completed"
            thrown(TerrainVisionAccessException)
    }

    def "should not complete terrain vision if it is already completed"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(ExpectedStageSample.BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        and: "$MARCIN_TERRAIN_VISIONER confirmed that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER confirmed project purposes map preparation need as $NECESSARY"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, NECESSARY)
        and: "$MARCIN_TERRAIN_VISIONER completes the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        when: "$MARCIN_TERRAIN_VISIONER tries to complete the terrain vision again"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is not completed again"
            thrown(TerrainVisionNotFoundException)
    }

}
