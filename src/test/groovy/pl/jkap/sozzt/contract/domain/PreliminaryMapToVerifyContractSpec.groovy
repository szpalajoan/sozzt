package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDataDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum
import spock.lang.Specification

class PreliminaryMapToVerifyContractSpec extends Specification implements ContractSample {

    def "Should confirm 'preliminary map to verify' step"() {
        given: "There is a contract with 'preliminary map to verify' step"
        ContractDataDto contractDataDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_VERIFY_STEP

        when: "confirm contract"
        contractFacade.confirmStep(contractDataDto.id)

        then: "contract has list of consents to add step"
        contractFacade.getContract(contractDataDto.id).contractStepEnum == ContractStepEnum.LIST_OF_CONSENTS_TO_ADD.toString()
    }
}
