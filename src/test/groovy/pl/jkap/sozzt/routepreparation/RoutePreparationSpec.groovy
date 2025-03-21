package pl.jkap.sozzt.routepreparation

import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.routepreparation.dto.MapVerificationDto
import pl.jkap.sozzt.routepreparation.exception.InvalidPersonResponsibleForRouteDrawingException
import pl.jkap.sozzt.routepreparation.exception.RoutePreparationNotFoundException
import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class RoutePreparationSpec extends SozztSpecification {

    def "should add route preparation when project purposes map preparation is completed"() {
        when: "terrain vision is completed"
            addKrynicaContractOnStage(COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION)
        then: "Project purposes map preparation is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION_KRYNICA_CONTRACT
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) == KRYNICA_ROUTE_PREPARATION
    }

    def "should not add route preparation when project purposes map preparation is not completed"() {
        given: "project purposes map preparation is not completed"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR trys to get $KRYNICA_ROUTE_PREPARATION"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId)
        then: "$KRYNICA_ROUTE_PREPARATION not exists"
            thrown(RoutePreparationNotFoundException)
    }

    def "should approve correctness of the map"() {
        given: "there is $KRYNICA_ROUTE_PREPARATION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$DANIEL_ROUTE_DRAWER is logged in"
            loginUser(DANIEL_ROUTE_DRAWER)
        and: "There is $TWO_WEEKS_AGO date"
            instantProvider.useFixedClock(TWO_WEEKS_AGO)
        when: "$DANIEL_ROUTE_DRAWER approves correctness of the map"
            routePreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        then: "Correctness of the map is approved"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) ==
                with(KRYNICA_ROUTE_PREPARATION, [mapVerification: MapVerificationDto.builder().verificationDate(TWO_WEEKS_AGO).correctnessOfTheMap(true).build()])
    }

    def "should choose person responsible for route drawing"() {
        given: "there is $KRYNICA_ROUTE_PREPARATION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$DANIEL_ROUTE_DRAWER is logged in"
            loginUser(DANIEL_ROUTE_DRAWER)
        and: "$DANIEL_ROUTE_DRAWER approves correctness of the map"
            routePreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        when: "$DANIEL_ROUTE_DRAWER chooses person responsible for route drawing"
            routePreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        then: "Person responsible for route drawing is chosen"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) ==
                with(KRYNICA_ROUTE_PREPARATION, [mapVerification: MapVerificationDto.builder().verificationDate(NOW).correctnessOfTheMap(true).build(),
                                                 routeDrawing   : KRYNICA_ROUTE_DRAWING])
    }

    def "should not choose person responsible for route drawing as none"() {
        given: "there is $KRYNICA_ROUTE_PREPARATION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$DANIEL_ROUTE_DRAWER is logged in"
            loginUser(DANIEL_ROUTE_DRAWER)
        and: "$DANIEL_ROUTE_DRAWER approves correctness of the map"
            routePreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        when: "$DANIEL_ROUTE_DRAWER tries to choose person responsible for route drawing as none"
            routePreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, null)
        then: "Exception is thrown"
            thrown(InvalidPersonResponsibleForRouteDrawingException)
    }

    def "should upload a drawn route"() {
        given: "there is $KRYNICA_ROUTE_PREPARATION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$DANIEL_ROUTE_DRAWER is logged in"
            loginUser(DANIEL_ROUTE_DRAWER)
        and: "$DANIEL_ROUTE_DRAWER approves correctness of the map"
            routePreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER chooses person responsible for route drawing"
            routePreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        when: "$DANIEL_ROUTE_DRAWER uploads a drawn route"
            FileDto fileDto = uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        then: "Route drawing is uploaded"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) ==
                with(KRYNICA_ROUTE_PREPARATION, [mapVerification: MapVerificationDto.builder().verificationDate(NOW).correctnessOfTheMap(true).build(),
                                                 routeDrawing   : with(KRYNICA_ROUTE_DRAWING, [mapWithRouteFileId: KRYNICA_MAP_WITH_ROUTE_METADATA.fileId])])
            fileDto == KRYNICA_MAP_WITH_ROUTE_METADATA
    }

    def "should upload a pdf with route and data"() {
        given: "there is $KRYNICA_ROUTE_PREPARATION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$DANIEL_ROUTE_DRAWER is logged in"
            loginUser(DANIEL_ROUTE_DRAWER)
        and: "$DANIEL_ROUTE_DRAWER approves correctness of the map"
            routePreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER chooses person responsible for route drawing"
            routePreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER uploads a drawn route"
            uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        when: "$DANIEL_ROUTE_DRAWER uploads a pdf with route and data"
            FileDto fileDto = uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, KRYNICA_CONTRACT.contractId)
        then: "Pdf with route and data is uploaded"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) ==
                with(KRYNICA_ROUTE_PREPARATION, [mapVerification: MapVerificationDto.builder().verificationDate(NOW).correctnessOfTheMap(true).build(),
                                                 routeDrawing: with(KRYNICA_ROUTE_DRAWING, [
                                                         mapWithRouteFileId: KRYNICA_MAP_WITH_ROUTE_METADATA.fileId,
                                                         routeWithDataFileId: KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA.fileId
                                                 ])])
            fileDto == KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA
    }

    def "should complete project purposes map preparation"() {
        given: "there is $KRYNICA_ROUTE_PREPARATION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$DANIEL_ROUTE_DRAWER is logged in"
            loginUser(DANIEL_ROUTE_DRAWER)
        and: "$DANIEL_ROUTE_DRAWER approves correctness of the map"
            routePreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER chooses person responsible for route drawing"
            routePreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER uploads a drawn route"
            uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER uploads a pdf with route and data"
            uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, KRYNICA_CONTRACT.contractId)
        when: "$DANIEL_ROUTE_DRAWER completes project purposes map preparation"
            routePreparationFacade.completeRoutePreparation(KRYNICA_CONTRACT.contractId)
        then: "Project purposes map preparation is completed"
            routePreparationFacade.getRoutePreparation(KRYNICA_CONTRACT.contractId) ==
                with(COMPLETED_KRYNICA_ROUTE_PREPARATION, [mapVerification: MapVerificationDto.builder().verificationDate(NOW).correctnessOfTheMap(true).build(),
                                                 routeDrawing: with(KRYNICA_ROUTE_DRAWING, [
                                                         mapWithRouteFileId: KRYNICA_MAP_WITH_ROUTE_METADATA.fileId,
                                                         routeWithDataFileId: KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA.fileId
                                                 ])])
    }
}
