package pl.jkap.sozzt.file.domain


import org.springframework.mock.web.MockMultipartFile
import pl.jkap.sozzt.config.application.ContractFileSpringEventPublisher
import spock.lang.Specification


class FileContractSystemStorageSpecData extends Specification implements FileSample {

    private FileWrapper fileWrapper
    private FileFacade fileContractFacade
    private ContractFileSpringEventPublisher contractSpringEventPublisher


    void setup() {
        fileWrapper = Stub(FileWrapper.class)
        contractSpringEventPublisher = Mock(ContractFileSpringEventPublisher.class)
        fileContractFacade = new FileFacade(new FileSystemStorage(fileWrapper), contractSpringEventPublisher, consentSpringEventPublisher)
    }


    def "Should add a scan from Tauron with event send"() {

        when: "User uploads scan from Tauron"
        fileWrapper.checkFileExist("upload_dir/1/fil22eName") >> true

        MockMultipartFile scanFromTauron = SCAN_FROM_TAURON
        fileContractFacade.storeContractFileInRepository(scanFromTauron, idContract, FileType.CONTRACT_SCAN_FROM_TAURON)

        then: "The scan file is saved"
        fileContractFacade.loadAsResource("fil22eName").filename == "fil22eName"

        and: "The event about uploading the scan is sent"
        1 * contractSpringEventPublisher.storeScanFromTauron(idContract)

        where:
        idContract = UUID.randomUUID()
    }


    def "should change file name to a unique name if there is already a file with the same name as the added file"() {

        given: "there are files with name "
        UUID sampleIdContract = UUID.fromString("7aeb1dee-67e2-49d6-b81c-ef29af62918b")
        fileWrapper.checkFileExist("upload_dir/" + sampleIdContract + "/CONTRACT_SCAN_FROM_TAURON/scan_123321") >> fileFirstExist
        fileWrapper.checkFileExist("upload_dir/" + sampleIdContract + "/CONTRACT_SCAN_FROM_TAURON/(1)scan_123321") >> fileSecondExist
        fileWrapper.checkFileExist("upload_dir/" + sampleIdContract + "/CONTRACT_SCAN_FROM_TAURON/(2)scan_123321") >> fileThirdExist

        when: "add new file"
        MockMultipartFile scanFromTauron = SCAN_FROM_TAURON
        String pathFile = fileContractFacade.storeContractFileInRepository(scanFromTauron, sampleIdContract, FileType.CONTRACT_SCAN_FROM_TAURON)

        then: " if a file with the same name already exists there is unique name"
        pathFile == newPathName


        where:
        fileFirstExist | fileSecondExist | fileThirdExist | newPathName
        false          | false           | false          | "upload_dir/7aeb1dee-67e2-49d6-b81c-ef29af62918b/CONTRACT_SCAN_FROM_TAURON/scan_123321"
        true           | false           | false          | "upload_dir/7aeb1dee-67e2-49d6-b81c-ef29af62918b/CONTRACT_SCAN_FROM_TAURON/(1)scan_123321"
        true           | true            | false          | "upload_dir/7aeb1dee-67e2-49d6-b81c-ef29af62918b/CONTRACT_SCAN_FROM_TAURON/(2)scan_123321"
    }

}