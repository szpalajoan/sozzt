package pl.jkap.sozzt.routepreparation

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.BEGIN_ROUTE_PREPARATION

class CompleteRoutePreparationStepSpec extends SozztSpecification {

    def "Should complete a route preparation"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_ROUTE_PREPARATION"
            uploadGeodeticMap(KRYNICA_ROUTE_PREPARATION, KRYNICA_GEODETIC_MAP)
        when: "$WALDEK_SURVEYOR completes the route preparation"
            routePreparationFacade.completeRoutePreparation(KRYNICA_CONTRACT.contractId)
        then: "Route preparation is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_ROUTE_PREPARATION_KRYNICA_CONTRACT
    }
}
