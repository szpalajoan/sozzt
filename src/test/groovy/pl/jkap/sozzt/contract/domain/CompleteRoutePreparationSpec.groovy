package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION

class CompleteRoutePreparationSpec extends SozztSpecification {

    def "should complete route preparation and should start contents collection when consents step is not started"() {
        given: "there is $KRYNICA_DOCUMENTATION stage"
            addKrynicaContractOnStage(COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$DANIEL_ROUTE_DRAWER approves correctness of the map"
            loginUser(DANIEL_ROUTE_DRAWER)
            routePreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER chooses person responsible for route drawing"
            routePreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER uploads a drawn route"
            uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER uploads a pdf with route and data"
            uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, KRYNICA_CONTRACT.contractId)
        when: "$DANIEL_ROUTE_DRAWER completes route preparation"
            routePreparationFacade.completeRoutePreparation(KRYNICA_CONTRACT.contractId)
        then: "Route preparation is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_ROUTE_PREPARATION_KRYNICA_CONTRACT
        }
    }
