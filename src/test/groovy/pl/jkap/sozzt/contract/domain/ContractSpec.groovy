package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.exception.ContractFinalizeException
import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedContractAdditionException
import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedContractFinalizeException
import pl.jkap.sozzt.contractsecurity.exception.UnauthorizedContractScanAdditionException
import pl.jkap.sozzt.sample.SozztSpecification

class ContractSpec extends SozztSpecification {


    def setup(){
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
    }

    def "Should add new contract"() {
        when: "$MONIKA_CONTRACT_INTRODUCER adds a new contract"
            ContractDto contract = contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        then: "New contract is added"
            contract == KRYNICA_CONTRACT
    }

    def "should not add contract if does not have permission"() {
        given: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        when: "$DAREK_PRELIMINARY_PLANER add new contract"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        then: "contract is not added"
            thrown(UnauthorizedContractAdditionException)
    }

    def "should add contract scan"() {
        given: "There is a $KRYNICA_CONTRACT added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$MONIKA_CONTRACT_INTRODUCER adds contract scan"
            addContractScan(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT.contractId)
        then: "Contract scan is added"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONTRACT, [isScanFromTauronUploaded : true])
    }

    def "should not add contract scan if does not have permission"() {
        given: "There is a $KRYNICA_CONTRACT added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        when: "$DAREK_PRELIMINARY_PLANER adds contract scan"
            addContractScan(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT.contractId)
        then: "Contract scan is not added"
            thrown(UnauthorizedContractScanAdditionException)
    }

    def "should finalize contract introduction"() {
        given: "There is a $KRYNICA_CONTRACT added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        and: "contract scan is added"
            addContractScan(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT.contractId)
        when: "$MONIKA_CONTRACT_INTRODUCER finalize introduction"
            contractFacade.finalizeContractIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Contract is introduced"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == INTRODUCED_KRYNICA_CONTRACT
    }

    def "should not finalize contract introduction if does not have permission"() {
        given: "There is a $KRYNICA_CONTRACT added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        and: "contract scan is added"
            addContractScan(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT.contractId)
        and: "$DAREK_PRELIMINARY_PLANER is logged in"
            loginUser(DAREK_PRELIMINARY_PLANER)
        when: "$DAREK_PRELIMINARY_PLANER finalize introduction"
            contractFacade.finalizeContractIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Contract is not introduced"
            thrown(UnauthorizedContractFinalizeException)
    }

    def "should not finalize contract introduction if scan is not uploaded"() {
        given: "There is a $KRYNICA_CONTRACT added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$MONIKA_CONTRACT_INTRODUCER finalize introduction"
            contractFacade.finalizeContractIntroduction(KRYNICA_CONTRACT.contractId)
        then: "Contract is not introduced"
            thrown(ContractFinalizeException)
    }
}
