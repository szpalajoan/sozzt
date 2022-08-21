package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum
import pl.jkap.sozzt.contract.exception.NoPreliminaryMapOnConfirmingException
import pl.jkap.sozzt.fileContract.event.UploadedPreliminaryMapSpringEvent
import spock.lang.Specification

class PreliminaryMapToUploadContractSpec extends Specification implements ContractSample {

    def "Should confirm 'preliminary map to upload' step"() {
        given: "There is a contract with 'preliminary map to upload' step"
        ContractDto contractDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        and: "preliminary map is uploaded"
        contractFacade.confirmPreliminaryMapUploaded(contractDto.id)

        when: "confirm contract"
        contractFacade.confirmStep(contractDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        contractFacade.getContract(contractDto.id).contactStepEnum == ContractStepEnum.PRELIMINARY_MAP_TO_VERIFY
    }

    def "Shouldn't confirm 'data input step' if scan file from Tauron isn't uploaded"() {
        given: "There is a contract with 'data input' without uploaded scan file from Tauron"
        ContractDto contractDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        and: "preliminary map isn't uploaded"
        !contractDto.preliminaryMapUpload

        when: "confirm contract"
        contractFacade.confirmStep(contractDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        thrown(NoPreliminaryMapOnConfirmingException)
    }

    def 'Should set scan uploaded when event came'() {
        given: "There is a contract with 'data input' without uploaded scan file from Tauron"
        ContractDto contractDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        when: "Event came about that the preliminary map has been uploaded"
        contractFacade.uploadedPreliminaryMap(new UploadedPreliminaryMapSpringEvent(any(), contractDto.id))

        then: "Contract have an information that preliminary map has been uploaded"
        contractFacade.getContract(contractDto.id).preliminaryMapUpload

    }
}
