package pl.jkap.sozzt.contract.domain


import pl.jkap.sozzt.contract.dto.ContractStepEnum
import pl.jkap.sozzt.contract.dto.DataInputContractDto
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException
import pl.jkap.sozzt.file.event.ScanFromTauronUploadedSpringEvent
import spock.lang.Specification

class DataInputContractSpec extends Specification implements ContractSample {

    def "Should add contract"() {

        when: "User add new contract"
        DataInputContractDto dataInputContractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        then: "New contract has 'data input' step"
        dataInputContractDto.contractDataDto.contactStepEnum == ContractStepEnum.DATA_INPUT

        and: "scan file isn't uploaded"
        !contractFacade.getContract(dataInputContractDto.contractDataDto.id).scanFromTauronUpload

    }

    def "Should confirm 'data input' step"() {
        given: "There is a contract with 'data input'"
        DataInputContractDto dataInputContractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        and: "this contract has uploaded scan file"
        contractFacade.confirmScanUploaded(dataInputContractDto.contractDataDto.id)

        when: "confirm contract"
        contractFacade.confirmStep(dataInputContractDto.contractDataDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        contractFacade.getContract(dataInputContractDto.contractDataDto.id).contractStepEnum == ContractStepEnum.PRELIMINARY_MAP_TO_UPLOAD.toString()
    }

    def "Shouldn't confirm 'data input step' if scan file from Tauron isn't uploaded"() {
        given: "There is a contract with 'data input' without uploaded scan file from Tauron"
        DataInputContractDto dataInputContractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        when: "confirm contract"
        contractFacade.confirmStep(dataInputContractDto.contractDataDto.id)

        then:
        thrown(NoScanFileOnConfirmingException)
    }

    def 'Should set scan uploaded when event came'() {
        given: "There is a contract with 'data input'"
        DataInputContractDto dataInputContractDto = contractFacade.addContract(NEW_MEDIUM_VOLTAGE_NETWORK_IN_TARNOW_CONTRACT)

        when: "Event came about that the scan file from Tauron has been uploaded"
        contractFacade.uploadedScanFromTauron(new ScanFromTauronUploadedSpringEvent(dataInputContractDto.contractDataDto.id))

        then: "Contract have an information that a scan has been uploaded"
        contractFacade.getContract(dataInputContractDto.contractDataDto.id).scanFromTauronUpload

    }
}
