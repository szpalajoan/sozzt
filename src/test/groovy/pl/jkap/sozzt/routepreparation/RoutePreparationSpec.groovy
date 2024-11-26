package pl.jkap.sozzt.routepreparation

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class RoutePreparationSpec extends SozztSpecification {

    def "should add route preparation"() {
        when: "terrain vision is completed"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION)
        then: "Route preparation is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) == KRYNICA_ROUTE_PREPARATION
    }
}
