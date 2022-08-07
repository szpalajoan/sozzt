package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException
import pl.jkap.sozzt.fileContract.event.FileUploadedSpringEvent
import spock.lang.Specification


class ContractSpec extends Specification implements ContractSample {

    ContractFacade contractFacade = new ContractConfiguration().contractFacade()

    def "Should add contract"() {

        when: "User add new contract"
        ContractDto contract = contractFacade.addContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        then: "New contract is added"
        contractFacade.getContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.id).getId() == MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.id

        and: "Contract step is 'data input'"
        contractFacade.getContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.id).contactStepEnum == ContractStepEnum.DATA_INPUT_STEP

        and: "scan file isn't uploaded"
        !contractFacade.getContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.id).isScanFromTauronUpload()

    }

    def "Should confirm 'data input' step"() {
        given: "There is a contract with 'data input'"
        ContractDto contract = contractFacade.addContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        and: "this contract has uploaded scan file"
        contractFacade.confirmScanUploaded(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.id)

        when: "confirm contract"
        contractFacade.confirmStep(contract.id)

        then: "contract has WaitingToPreliminaryMapStep"
        contractFacade.getContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.id).contactStepEnum == ContractStepEnum.WAITING_TO_PRELIMINARY_MAP_STEP
    }

    def "Shouldn't confirm 'data input step' if scan file from Tauron isn't uploaded"() {
        given: "There is a contract with 'data input' without uploaded scan file from Tauron"
        ContractDto contract = contractFacade.addContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        when: "confirm contract"
        contractFacade.confirmStep(contract.id)

        then: "contract has WaitingToPreliminaryMapStep"
        thrown(NoScanFileOnConfirmingException)
    }

    def 'Should set scan uploaded when event came'() {
        given: "There is a contract with 'data input'"
        contractFacade.addContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        when: "Event came about that the scan file from Tauron has been uploaded"
        contractFacade.uploadedScanFromTauron(new FileUploadedSpringEvent(any(), MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.getId()))

        then: "Contract have an information that a scan has been uploaded"
        contractFacade.getContract(MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT.id).scanFromTauronUpload

    }
}
