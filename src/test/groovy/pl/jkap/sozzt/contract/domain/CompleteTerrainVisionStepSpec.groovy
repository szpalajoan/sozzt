package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*
import static pl.jkap.sozzt.terrainvision.domain.ProjectPurposesMapPreparationNeed.*

class CompleteTerrainVisionStepSpec extends SozztSpecification {

    def "Should complete a terrain vision and begin the preparation of project purposes map and land extracts"() {
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
        then: "Terrain vision is completed and project purposes map preparation and land extracts are started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION
            landExtractsFacade.getLandExtracts(KRYNICA_CONTRACT.contractId) == KRYNICA_LAND_EXTRACTS
    }

    def "Should complete a terrain vision and begin route preparation and land extracts when project purposes map preparation is not necessary"() {
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
        then: "Terrain vision is completed and route preparation and land extracts are started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_ROUTE_PREPARATION_STEP
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) == KRYNICA_ROUTE_PREPARATION
            landExtractsFacade.getLandExtracts(KRYNICA_CONTRACT.contractId) == KRYNICA_LAND_EXTRACTS
    }
}
