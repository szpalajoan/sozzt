package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.sample.SozztSpecification

import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.DONE
import static pl.jkap.sozzt.contract.dto.ContractStepDto.ContractStepStatusDto.IN_PROGRESS
import static pl.jkap.sozzt.sample.ExpectedStageSample.BEGIN_PROJECT_PURPOSES_MAP_PREPARATION

class CompleteProjectPurposesMapPreparationStepSpec extends SozztSpecification {

    def "Should complete a project purposes map preparation"() {
        given: "there is $KRYNICA_TERRAIN_VISION stage"
            addKrynicaContractOnStage(BEGIN_PROJECT_PURPOSES_MAP_PREPARATION)
        and: "$WALDEK_SURVEYOR is logged in"
            loginUser(WALDEK_SURVEYOR)
        and: "$WALDEK_SURVEYOR added geodetic map to $KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION"
            uploadGeodeticMap(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, KRYNICA_GEODETIC_MAP)
        when: "$WALDEK_SURVEYOR completes the project purposes map preparation"
            projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(KRYNICA_CONTRACT.contractId)
        then: "project purposes map preparation is completed"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION_KRYNICA_CONTRACT
    }
}
