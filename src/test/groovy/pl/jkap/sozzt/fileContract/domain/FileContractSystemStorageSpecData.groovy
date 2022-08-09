package pl.jkap.sozzt.fileContract.domain


import org.springframework.mock.web.MockMultipartFile
import pl.jkap.sozzt.config.application.ContractSpringEventPublisher
import spock.lang.Specification


class FileContractSystemStorageSpecData extends Specification implements FileSample {

    private FileWrapper fileWrapper
    private FileContractFacade fileContractFacade
    private ContractSpringEventPublisher contractSpringEventPublisher


    void setup() {
        fileWrapper = Stub(FileWrapper.class)
        contractSpringEventPublisher = Mock(ContractSpringEventPublisher.class)
        fileContractFacade = new FileContractFacade(new FileSystemStorage(contractSpringEventPublisher, fileWrapper))
    }


    def "Should add a scan from Tauron with event send"() {

        when: "User uploads scan from Tauron"
        fileWrapper.checkFileExist("upload_dir/1/fil22eName") >> true

        MockMultipartFile scanFromTauron = SCAN_FROM_TAURON
        fileContractFacade.store(scanFromTauron, idContract, FileType.CONTRACT_SCAN_FROM_TAURON)

        then: "The scan file is saved"
        fileContractFacade.loadAsResource("fil22eName").filename == "fil22eName"

        and: "The event about uploading the scan is sent"
        1 * contractSpringEventPublisher.publishCustomEvent(idContract)

        where:
        idContract = 1
    }


    def "should change file name to a unique name if there is already a file with the same name as the added file"() {

        given: "there are files with name "
        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321") >> fileFirstExist
        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/(1)scan_123321") >> fileSecondExist
        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/(2)scan_123321") >> fileThirdExist

        when: "add new file"
        MockMultipartFile scanFromTauron = SCAN_FROM_TAURON
        String pathFile = fileContractFacade.store(scanFromTauron, idContract, FileType.CONTRACT_SCAN_FROM_TAURON)

        then: " if a file with the same name already exists there is unique name"
        pathFile == newPathName


        where:
        fileFirstExist | fileSecondExist | fileThirdExist | newPathName
        false          | false           | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321"
        true           | false           | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/(1)scan_123321"
        true           | true            | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/(2)scan_123321"

        idContract = 1
    }

}