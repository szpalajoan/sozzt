package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException
import pl.jkap.sozzt.fileContract.event.UploadedScanFromTauronSpringEvent
import spock.lang.Specification


class DataInputContractSpec extends Specification implements ContractSample {

    ContractFacade contractFacade = new ContractConfiguration().contractFacade()

    def "Should add contract"() {

        when: "User add new contract"
        ContractDto contractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        then: "New contract has 'data input' step"
        contractFacade.getContract(contractDto.id).contactStepEnum == ContractStepEnum.DATA_INPUT_STEP

        and: "scan file isn't uploaded"
        !contractFacade.getContract(contractDto.id).isScanFromTauronUpload()

    }

    def "Should confirm 'data input' step"() {
        given: "There is a contract with 'data input'"
        ContractDto contractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        and: "this contract has uploaded scan file"
        contractFacade.confirmScanUploaded(contractDto.id)

        when: "confirm contract"
        contractFacade.confirmStep(contractDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        contractFacade.getContract(contractDto.id).contactStepEnum == ContractStepEnum.PRELIMINARY_MAP_TO_UPLOAD
    }

    def "Shouldn't confirm 'data input step' if scan file from Tauron isn't uploaded"() {
        given: "There is a contract with 'data input' without uploaded scan file from Tauron"
        ContractDto contractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        when: "confirm contract"
        contractFacade.confirmStep(contractDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        thrown(NoScanFileOnConfirmingException)
    }

    def 'Should set scan uploaded when event came'() {
        given: "There is a contract with 'data input'"
        ContractDto contractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        when: "Event came about that the scan file from Tauron has been uploaded"
        contractFacade.uploadedScanFromTauron(new UploadedScanFromTauronSpringEvent(any(), contractDto.id))

        then: "Contract have an information that a scan has been uploaded"
        contractFacade.getContract(contractDto.id).scanFromTauronUpload

    }
}
