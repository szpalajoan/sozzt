package pl.jkap.sozzt.filestorage.domain


import org.springframework.mock.web.MockMultipartFile
import pl.jkap.sozzt.filestorage.event.FileContractSpringEventPublisher
import spock.lang.Specification


class FileContractStorageSpec extends FileStorageBaseSpec {

    private FileWrapper fileWrapper
    private FileStorageFacade fileContractFacade
    private FileContractSpringEventPublisher contractSpringEventPublisher


    void setup() {
        fileWrapper = new FileWrapperImpl()
        contractSpringEventPublisher = Mock(FileContractSpringEventPublisher.class)
        fileContractFacade = new FileStorageFacade(new FileSystemStorage(fileWrapper, contractSpringEventPublisher))
    }


    def "Should add a scan from Tauron to the contract"(){
        when: "$MONIKA_CONTRACT_INTRODUCER uploads scan from Tauron"
            fileContractFacade.store(KRYNICA_CONTRACT_SCAN, idContract, FileType.CONTRACT_SCAN_FROM_TAURON)

        then: "The scan file is saved"
            fileContractFacade.loadAsResource("fil22eName").filename == "fil22eName"

        and: "The event about uploading the scan is sent"
        1 * contractSpringEventPublisher.fileUploaded(idContract)

    }


    def "should change file name to a unique name if there is already a file with the same name as the added file"() {

        given: "there are files with name "
        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321") >> fileFirstExist
        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (1)") >> fileSecondExist
        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (2)") >> fileThirdExist

        when: "add new file"
        MockMultipartFile scanFromTauron = SCAN_FROM_TAURON
        String pathFile = fileContractFacade.store(scanFromTauron, idContract, FileType.CONTRACT_SCAN_FROM_TAURON)

        then: " if a file with the same name already exists there is unique name"
        pathFile == newPathName


        where:
        fileFirstExist | fileSecondExist | fileThirdExist | newPathName
        false          | false           | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321"
        true           | false           | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (1)"
        true           | true            | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (2)"

        idContract = 1
    }

}