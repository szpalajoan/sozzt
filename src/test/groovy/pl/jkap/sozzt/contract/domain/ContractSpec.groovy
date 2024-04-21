package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirming
import pl.jkap.sozzt.filestorage.event.ContractScanAddedEvent

class ContractSpec extends ContractBaseSpec {


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

//    def "Should confirm 'data input' step"() {
//        given: "There is a contract with 'data input'"
//        ContractDto contract = contractFacade.addContract(KRYNICA_CONTRACT)
//
//        and: "this contract has uploaded scan file"
//        contractFacade.confirmScanUploaded(KRYNICA_CONTRACT.contractId)
//
//        when: "confirm contract"
//        contractFacade.confirmStep(contract.contractId)
//
//        then: "contract has WaitingToPreliminaryMapStep"
//        contractFacade.getContract(KRYNICA_CONTRACT.contractId).contactStep == ContractDto.ContactStepDto.WAITING_TO_PRELIMINARY_MAP_STEP
//    }

//    def "Shouldn't confirm 'data input step' if scan file from Tauron isn't uploaded"(){
//        given: "There is a contract with 'data input' without uploaded scan file from Tauron"
//        ContractDto contract = contractFacade.addContract(KRYNICA_CONTRACT)
//        !contractFacade.checkIsScanFromTauronUploaded(KRYNICA_CONTRACT.contractId)
//
//        when: "confirm contract"
//        contractFacade.confirmStep(contract.contractId)
//
//        then: "contract has WaitingToPreliminaryMapStep"
//        thrown(NoScanFileOnConfirming)
//    }
//
//    def 'Should set scan uploaded when event came'() {
//        given: "There is a contract with 'data input'"
//        contractFacade.addContract(KRYNICA_CONTRACT)
//
//        when: "Event came about that the scan file from Tauron has been uploaded"
//        contractFacade.onContractScanAddedEvent(new ContractScanAddedEvent(KRYNICA_CONTRACT.contractId))
//
//        then: "Contract have an information that a scan has been uploaded"
//        contractFacade.checkIsScanFromTauronUploaded(KRYNICA_CONTRACT.contractId)
//
//    }
}
