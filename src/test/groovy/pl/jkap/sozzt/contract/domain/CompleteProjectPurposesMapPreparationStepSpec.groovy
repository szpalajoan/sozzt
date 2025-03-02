package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.DONE
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.IN_PROGRESS
import static pl.jkap.sozzt.sample.ExpectedStageSample.BEGIN_ROUTE_PREPARATION

class CompleteProjectPurposesMapPreparationStepSpec extends SozztSpecification {

    def "Should complete a project purposes map preparation"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        when: "$WALDEK_SURVEYOR completes the project purposes map preparation"
            projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId)
        then: "project purposes map preparation is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_ROUTE_PREPARATION_KRYNICA_CONTRACT
    }

    def "Should complete a project purposes map preparation and begin the preparation of documentation step when consents collection is completed"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_ROUTE_PREPARATION)
        and: "$KASIA_CONSENT_CORDINATOR completes the consents collection"
            loginUser(KASIA_CONSENT_CORDINATOR)
            completeConsentsCollection(COMPLETED_KRYNICA_CONSENTS)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        when: "$WALDEK_SURVEYOR completes the project purposes map preparation"
            projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId)
        then: "project purposes map preparation is completed and preparation of documentation step is started"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == with(COMPLETED_ROUTE_PREPARATION_KRYNICA_CONTRACT, [
                    contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                                    with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                                    with(KRYNICA_CONTRACT_ROUTE_PREPARATION_STEP, [contractStepStatus: DONE]),
                                    with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: DONE]),
                                    with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: IN_PROGRESS])]])
    }
}
