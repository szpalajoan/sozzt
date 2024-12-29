package pl.jkap.sozzt.terrainvision


import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*
import static pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto.RoutePreparation.NECESSARY

class CompleteTerrainVisionStepSpec extends SozztSpecification {

    def "Should complete a terrain vision"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$MARCIN_TERRAIN_VISIONER is logged in"
            loginUser(MARCIN_TERRAIN_VISIONER)
        and: "$MARCIN_TERRAIN_VISIONER confirmed that all photos are uploaded"
            terrainVisionFacade.confirmAllPhotosAreUploaded(KRYNICA_CONTRACT.contractId)
        and: "$MARCIN_TERRAIN_VISIONER confirmed changes on map as $NECESSARY"
            terrainVisionFacade.setRoutePreparationNecessary(KRYNICA_CONTRACT.contractId, NECESSARY)
        when: "$MARCIN_TERRAIN_VISIONER completes the terrain vision"
            terrainVisionFacade.completeTerrainVision(KRYNICA_CONTRACT.contractId)
        then: "Terrain vision is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
    }


}
