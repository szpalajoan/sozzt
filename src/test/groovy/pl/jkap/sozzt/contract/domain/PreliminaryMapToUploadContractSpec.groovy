package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractStepEnum
import pl.jkap.sozzt.contract.dto.DataInputContractDto
import pl.jkap.sozzt.contract.exception.NoPreliminaryMapOnConfirmingException
import pl.jkap.sozzt.fileContract.event.PreliminaryMapUploadedSpringEvent
import spock.lang.Specification

class PreliminaryMapToUploadContractSpec extends Specification implements ContractSample {

    def "Should confirm 'preliminary map to upload' step"() {
        given: "There is a contract with 'preliminary map to upload' step"
        DataInputContractDto dataInputContractDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        and: "preliminary map is uploaded"
        contractFacade.confirmPreliminaryMapUploaded(dataInputContractDto.contractDataDto.id)

        when: "confirm contract"
        contractFacade.confirmStep(dataInputContractDto.contractDataDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        contractFacade.getContract(dataInputContractDto.contractDataDto.id).contractStepEnum == ContractStepEnum.PRELIMINARY_MAP_TO_VERIFY.toString()
    }

    def "Shouldn't confirm 'data input step' if scan file from Tauron isn't uploaded"() {
        given: "There is a contract with 'data input' without uploaded scan file from Tauron and preliminary map isn't uploaded"
        DataInputContractDto dataInputContractDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        when: "confirm contract"
        contractFacade.confirmStep(dataInputContractDto.contractDataDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        thrown(NoPreliminaryMapOnConfirmingException)
    }

    def 'Should set scan uploaded when event came'() {
        given: "There is a contract with 'data input' without uploaded scan file from Tauron"
        DataInputContractDto dataInputContractDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        when: "Event came about that the preliminary map has been uploaded"
        contractFacade.uploadedPreliminaryMap(new PreliminaryMapUploadedSpringEvent(dataInputContractDto.contractDataDto.id))

        then: "Contract have an information that preliminary map has been uploaded"
        contractFacade.getContract(dataInputContractDto.contractDataDto.id).preliminaryMapUpload

    }
}
