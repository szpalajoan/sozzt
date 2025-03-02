package pl.jkap.sozzt.projectpurposesmappreparation

import pl.jkap.sozzt.projectpurposesmappreparation.exception.ProjectPurposesMapPreparationNotFoundException
import pl.jkap.sozzt.projectpurposesmappreparation.exception.InvalidPersonResponsibleForRouteDrawingException
import pl.jkap.sozzt.sample.ContractFixture
import pl.jkap.sozzt.sample.SozztSpecification
import pl.jkap.sozzt.filestorage.dto.FileDto

import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class ProjectPurposesMapPreparationSpec extends SozztSpecification {

    def "should add project purposes map preparation when terrain vision is completed"() {
        when: "terrain vision is completed"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION)
        then: "Project purposes map preparation is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION
    }

    def "should not begin project purposes map preparation step when map is not required on terrain vision stage"() {
        when: "terrain vision is completed and map is not required"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION, new ContractFixture().withMapRequired(false))
        then: "Project purposes map preparation is not started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_TERRAIN_VISION_KRYNICA_CONTRACT_WITH_NOT_NECESSARY_ROUTE_PREPARATION_STEP
    }

    def "should not add project purposes map preparation when map is not required on terrain vision stage"() {
        given: "terrain vision is completed and map is not required"
            addKrynicaContractOnStage(COMPLETED_TERRAIN_VISION, new ContractFixture().withMapRequired(false))
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR trys to get $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId)
        then: "$KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION not exists"
            thrown(ProjectPurposesMapPreparationNotFoundException)
    }

    def "should not add project purposes map preparation when terrain vision is not completed"() {
        given: "terrain vision is not completed"
            addKrynicaContractOnStage(BEGIN_TERRAIN_VISION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR trys to get $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId)
        then: "$KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION not exists"
            thrown(ProjectPurposesMapPreparationNotFoundException)
    }

    def "should add geodetic map to project purposes map preparation"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR adds geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        then: "Geodetic map is added to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, [isGeodeticMapUploaded: true])
    }

    def "should approve correctness of the map"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        when: "$WALDEK_SURVEYOR approves correctness of the map"
            projectPurposesMapPreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        then: "Correctness of the map is approved"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == 
                with(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, [correctnessOfTheMap: true,
                                                               isGeodeticMapUploaded: true])
    }

    def "should choose person responsible for route drawing"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        and: "$WALDEK_SURVEYOR approves correctness of the map"
            projectPurposesMapPreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        when: "$WALDEK_SURVEYOR chooses person responsible for route drawing"
            projectPurposesMapPreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        then: "Person responsible for route drawing is chosen"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == 
                with(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, [
                    correctnessOfTheMap: true,
                    isGeodeticMapUploaded: true,
                    routeDrawing: KRYNICA_ROUTE_DRAWING
                ])
    }

    def "should not choose person responsible for route drawing as none"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        and: "$WALDEK_SURVEYOR approves correctness of the map"
            projectPurposesMapPreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        when: "$WALDEK_SURVEYOR tries to choose person responsible for route drawing as none"
            projectPurposesMapPreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, null)
        then: "Exception is thrown"
            thrown(InvalidPersonResponsibleForRouteDrawingException)
    }

    def "should upload a drawn route"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        and: "$WALDEK_SURVEYOR approves correctness of the map"
            projectPurposesMapPreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$WALDEK_SURVEYOR chooses person responsible for route drawing"
            projectPurposesMapPreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER is logged in"
            loginUser(DANIEL_ROUTE_DRAWER)
        when: "$DANIEL_ROUTE_DRAWER uploads a drawn route"
            FileDto fileDto = uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        then: "Route drawing is uploaded"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == 
                with(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, [
                    correctnessOfTheMap: true,
                    isGeodeticMapUploaded: true,
                    routeDrawing: with(KRYNICA_ROUTE_DRAWING, [mapWithRouteFileId: KRYNICA_MAP_WITH_ROUTE_METADATA.fileId])
                ])
            fileDto == KRYNICA_MAP_WITH_ROUTE_METADATA
    }

    def "should upload a pdf with route and data"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        and: "$WALDEK_SURVEYOR approves correctness of the map"
            projectPurposesMapPreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$WALDEK_SURVEYOR chooses person responsible for route drawing"
            projectPurposesMapPreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER uploads a drawn route"
            loginUser(DANIEL_ROUTE_DRAWER)
            uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        when: "$DANIEL_ROUTE_DRAWER uploads a pdf with route and data"
            FileDto fileDto = uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, KRYNICA_CONTRACT.contractId)
        then: "Pdf with route and data is uploaded"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == 
                with(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, [
                    correctnessOfTheMap: true,
                    isGeodeticMapUploaded: true,
                    routeDrawing: with(KRYNICA_ROUTE_DRAWING, [
                        mapWithRouteFileId: KRYNICA_MAP_WITH_ROUTE_METADATA.fileId,
                        routeWithDataFileId: KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA.fileId
                    ])
                ])
            fileDto == KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA
    }

    def "should complete project purposes map preparation"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        and: "$WALDEK_SURVEYOR approves correctness of the map"
            projectPurposesMapPreparationFacade.approveCorrectnessOfTheMap(KRYNICA_CONTRACT.contractId)
        and: "$WALDEK_SURVEYOR chooses person responsible for route drawing"
            projectPurposesMapPreparationFacade.choosePersonResponsibleForRouteDrawing(KRYNICA_CONTRACT.contractId, DANIEL_ROUTE_DRAWER.name)
        and: "$DANIEL_ROUTE_DRAWER uploads a drawn route"
            loginUser(DANIEL_ROUTE_DRAWER)
            uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, KRYNICA_CONTRACT.contractId)
        and: "$DANIEL_ROUTE_DRAWER uploads a pdf with route and data"
            uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, KRYNICA_CONTRACT.contractId)
        when: "$DANIEL_ROUTE_DRAWER completes project purposes map preparation"
            projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId)
        then: "Project purposes map preparation is completed"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) ==
                    with(COMPLETED_KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, [routeDrawing: with(KRYNICA_ROUTE_DRAWING, [
                            mapWithRouteFileId: KRYNICA_MAP_WITH_ROUTE_METADATA.fileId,
                            routeWithDataFileId: KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA.fileId
                    ])])
    }
}
