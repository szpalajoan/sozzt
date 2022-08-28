package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDataDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum
import spock.lang.Specification

class ListOfConsentsToAddContractContractSpec extends Specification implements ContractSample {

    def "Should confirm 'list of consents to add' step"() {
        given: "There is a contract with 'list of consents to add' step"
        ContractDataDto contractDataDto = CONTRACT_WITH_LIST_OF_CONSENT_TO_ADD_STEP

        when: "confirm contract"
        contractFacade.confirmStep(contractDataDto.id)

        then: "contract has consents to accept step"
        contractFacade.getContract(contractDataDto.id).contractStepEnum == ContractStepEnum.CONSENTS_TO_ACCEPT.toString()
    }

}
