package pl.jkap.sozzt.contract.domain


import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*
import static pl.jkap.sozzt.terrainvision.domain.ProjectPurposesMapPreparationNeed.*

class CompleteTerrainVisionStepSpec extends SozztSpecification {

    def "Should complete a terrain vision and begin the preparation of project purposes map and consents collection"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        and: "$MARCIN_TERRAIN_VISIONER confirmed that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER confirmed that project purposes map preparation is necessary"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, NECESSARY)
        when: "$MARCIN_TERRAIN_VISIONER completes the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
    }

    def "Should complete a terrain vision and begin only consents collection"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        and: "$MARCIN_TERRAIN_VISIONER confirmed that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER sets that project purposes map preparation is not need"
            terrainVisionFacade.setProjectPurposesMapPreparationNeed(KRYNICA_CONTRACT.contractId, NOT_NEED)
        when: "$MARCIN_TERRAIN_VISIONER completes the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_ROUTE_PREPARATION_STEP
    }

}
