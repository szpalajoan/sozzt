package pl.jkap.sozzt.projectpurposesmappreparation

import pl.jkap.sozzt.projectpurposesmappreparation.exception.ProjectPurposesMapPreparationNotFoundException
import pl.jkap.sozzt.sample.ContractFixture
import pl.jkap.sozzt.sample.SozztSpecification

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
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        when: "$WALDEK_SURVEYOR adds geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        then: "Geodetic map is added to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId) == with(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, [isGeodeticMapUploaded: true])
    }
    //TODO dodaj zabezpieczenie aby nie było można robić geta stepow bez uprawnien
}
