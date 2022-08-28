package pl.jkap.sozzt.contract.domain

import pl.jkap.sozzt.contract.dto.ContractDataDto
import pl.jkap.sozzt.contract.dto.ContractStepEnum
import pl.jkap.sozzt.contract.exception.NoPreliminaryMapOnConfirmingException
import pl.jkap.sozzt.fileContract.event.PreliminaryMapUploadedSpringEvent
import spock.lang.Specification

class PreliminaryMapToUploadContractSpec extends Specification implements ContractSample {

    def "Should confirm 'preliminary map to upload' step"() {
        given: "There is a contract with 'preliminary map to upload' step"
        ContractDataDto contractDataDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        and: "preliminary map is uploaded"
        contractFacade.confirmPreliminaryMapUploaded(contractDataDto.id)

        when: "confirm contract"
        contractFacade.confirmStep(contractDataDto.id)

        then: "contract has WaitingToPreliminaryMapStep"
        contractFacade.getContract(contractDataDto.id).contractStepEnum == ContractStepEnum.PRELIMINARY_MAP_TO_VERIFY.toString()
    }

    def "Shouldn't confirm 'preliminary map to upload' if preliminary map isn't uploaded"() {
        given: "There is a contract with 'preliminary map to upload' without uploaded preliminary map"
        ContractDataDto contractDataDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        when: "confirm contract"
        contractFacade.confirmStep(contractDataDto.id)

        then:
        thrown(NoPreliminaryMapOnConfirmingException)
    }

    def 'Should set that preliminary map is uploaded when event came'() {
        given: "There is a contract with 'preliminary map to upload' without preliminary map uploaded"
        ContractDataDto contractDataDto = CONTRACT_WITH_PRELIMINARY_MAP_TO_UPLOAD_STEP

        when: "Event came about that the preliminary map has been uploaded"
        contractFacade.uploadedPreliminaryMap(new PreliminaryMapUploadedSpringEvent(contractDataDto.id))

        then: "Contract have an information that preliminary map has been uploaded"
        contractFacade.getContract(contractDataDto.id).preliminaryMapUpload
    }
}
