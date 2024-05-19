package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirming
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent
import pl.jkap.sozzt.sample.SozztSpecification

class ContractSpec extends SozztSpecification {


    def setup(){
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
    }

    def "Should add new contract"() {
        when: "$MONIKA_CONTRACT_INTRODUCER add new contract"
            ContractDto contract = contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))

        then: "New contract is added"
            contract == KRYNICA_CONTRACT
    }

    //todo test finalize introduction
}
