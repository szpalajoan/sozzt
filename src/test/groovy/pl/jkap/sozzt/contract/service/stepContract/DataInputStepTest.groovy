package pl.jkap.sozzt.contract.service.stepContract

import org.springframework.web.server.ResponseStatusException
import pl.jkap.sozzt.contract.old.service.stepContract.DataInputStep
import pl.jkap.sozzt.contract.old.service.stepContract.Step
import pl.jkap.sozzt.contract.old.service.stepContract.WaitingToPreliminaryStep
import pl.jkap.sozzt.fileContract.domain.FileContract
import pl.jkap.sozzt.contract.old.model.FileType
import pl.jkap.sozzt.contract.old.repository.FileContractRepository
import spock.lang.Specification

class DataInputStepTest extends Specification {


    private FileContractRepository fileContractRepository

    void setup() {
        fileContractRepository = Stub(FileContractRepository.class)
    }

    def "Administrator can validate data input step that has a file with contract from tauron"() {
        given: "There is contract with data input step "
        Step actualStep = new DataInputStep(idContract, fileContractRepository)

        and: "and this contract has file with contract from tauron"
        FileContract fileContract = new FileContract()
        fileContract.setFileType(uploadedFileType)
        fileContractRepository.findAnyByIdContractAndFileType(idContract, FileType.CONTRACT_FROM_TAURON) >> fileContract

        when: "administrator validates this contract"
        Step newStep = actualStep.validateStep()

        then: "step waiting to preliminary is returned"
        newStep.class == newStepClass

        where:
        uploadedFileType = FileType.CONTRACT_FROM_TAURON
        newStepClass = WaitingToPreliminaryStep.class
        idContract = 1

    }

    def "Administrator can't validate data input step that hasn't have a file with contract from tauron"() {
        given: "There is contract with data input step "
        Step actualStep = new DataInputStep(idContract, fileContractRepository)

        and: "and this contract has file with photo from place of the contract"
        fileContractRepository.findAnyByIdContractAndFileType(idContract, FileType.CONTRACT_FROM_TAURON) >> null

        when: "administrator validates this contract"
        actualStep.validateStep()

        then: "there is information about the lack of attachment with type 'contract from tauron'"
        Exception exception = thrown(ResponseStatusException)
        exception.reason == DataInputStep.INFO_ABOUT_NONE_FILE

        where:
        uploadedFileType = FileType.PHOTO_FROM_PLACE_OF_THE_CONTRACT
        idContract = 1

    }

}
