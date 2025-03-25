package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.DONE
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.IN_PROGRESS
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.ON_HOLD
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.ON_HOLD
import static pl.jkap.sozzt.sample.ExpectedStageSample.BEGIN_PROJECT_PURPOSES_MAP_PREPARATION

class CompleteProjectPurposesMapPreparationStepSpec extends SozztSpecification {

    def "Should complete a project purposes map preparation and begin route preparation"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        when: "$WALDEK_SURVEYOR completes the project purposes map preparation"
            projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId)
        then: "project purposes map preparation is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == with(COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION_KRYNICA_CONTRACT, [
                    contractSteps: [with(KRYNICA_CONTRACT_PRELIMINARY_PLAN_STEP, [contractStepStatus: DONE]),
                                    with(KRYNICA_CONTRACT_TERRAIN_VISION_STEP, [contractStepStatus: DONE]),
                                    with(KRYNICA_PROJECT_PURPOSES_MAP_PREPARATION_STEP, [contractStepStatus: DONE]),
                                    with(KRYNICA_ROUTE_PREPARATION_STEP, [contractStepStatus: IN_PROGRESS]),
                                    with(KRYNICA_LAND_EXTRACTS_STEP, [contractStepStatus: IN_PROGRESS]),
                                    with(KRYNICA_CONSENTS_COLLECTION_STEP, [contractStepStatus: ON_HOLD]),
                                    with(KRYNICA_PREPARATION_OF_DOCUMENTATION_STEP, [contractStepStatus: ON_HOLD])]])
    }
}
