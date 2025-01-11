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

    def "should not add route preparation when terrain vision is not completed"() {
        given: "terrain vision is not completed"
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR trys to get $KRYNICA_ROUTE_PREPARATION"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId)
        then: "$KRYNICA_ROUTE_PREPARATION not exists"
            thrown(RoutePreparationNotFoundException)
    }

    def "should add geodetic map to route preparation"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR adds geodetic map to $KRYNICA_ROUTE_PREPARATION"
            uploadGeodeticMap(KRYNICA_ROUTE_PREPARATION, KRYNICA_GEODETIC_MAP)
        then: "Geodetic map is added to $KRYNICA_ROUTE_PREPARATION"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_ROUTE_PREPARATION, [isGeodeticMapUploaded : true])
    }
    //TODO dodaj zabezpieczenie aby nie było można robić geta stepow bez uprawnien
}
