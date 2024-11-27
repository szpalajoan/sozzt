package pl.jkap.sozzt.routepreparation

import pl.jkap.sozzt.routepreparation.exception.RoutePreparationNotFoundException
import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class RoutePreparationSpec extends SozztSpecification {

    def "should add route preparation when terrain vision is completed"() {
        when: "terrain vision is completed"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION)
        then: "Route preparation is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) == KRYNICA_ROUTE_PREPARATION
    }

    def "should not begin route preparation step when map is not required on terrain vision stage"() {
        when: "terrain vision is completed and map is not required"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION_WITHOUT_MAP_REQUIRED)
        then: "Route preparation is not necessary"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_ROUTE_PREPARATION_STEP
    }

    def "should not add route preparation when map is not required on terrain vision stage"() {
        given: "terrain vision is completed and map is not required"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION_WITHOUT_MAP_REQUIRED)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR trys to get $KRYNICA_ROUTE_PREPARATION"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId)
        then: "$KRYNICA_ROUTE_PREPARATION not exists"
            thrown(RoutePreparationNotFoundException)
    }

    //TODO dodaj zabezpieczenie aby nie było można robić geta stepow bez uprawnien
}
