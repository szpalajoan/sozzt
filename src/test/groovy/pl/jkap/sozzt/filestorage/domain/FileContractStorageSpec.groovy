package pl.jkap.sozzt.filestorage.domain

import pl.jkap.sozzt.filestorage.dto.FileDto

class FileContractStorageSpec extends FileStorageBaseSpec {

    def "Should add a scan from Tauron to the contract"(){
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        and: "there is a $KRYNICA_CONTRACT contract added by $MONIKA_CONTRACT_INTRODUCER"
            def x = KRYNICA_CONTRACT
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_CONTRACT_SCAN_FILE to $KRYNICA_CONTRACT contract"
            FileDto fileDto = fileStorageFacade.addContractScan(toAddContractScanFileDto(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT_SCAN_FILE, KRYNICA_CONTRACT))
        then: "$KRYNICA_CONTRACT_SCAN_FILE is added to $KRYNICA_CONTRACT contract"
            fileDto == KRYNICA_CONTRACT_SCAN
            //pobierz plik
            //sprawdz czy plik dodany do kontraktu
        and: "The event about uploading the scan is sent"
       // 1 * contractSpringEventPublisher.fileUploaded(idContract)

    }


//    def "should change file name to a unique name if there is already a file with the same name as the added file"() {
//
//        given: "there are files with name "
//        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321") >> fileFirstExist
//        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (1)") >> fileSecondExist
//        fileWrapper.checkFileExist("upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (2)") >> fileThirdExist
//
//        when: "add new file"
//        MockMultipartFile scanFromTauron = SCAN_FROM_TAURON
//        String pathFile = fileStorageFacade.addContractScan(scanFromTauron, idContract, FileType.CONTRACT_SCAN_FROM_TAURON)
//
//        then: " if a file with the same name already exists there is unique name"
//        pathFile == newPathName
//
//
//        where:
//        fileFirstExist | fileSecondExist | fileThirdExist | newPathName
//        false          | false           | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321"
//        true           | false           | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (1)"
//        true           | true            | false          | "upload_dir/1/CONTRACT_SCAN_FROM_TAURON/scan_123321 (2)"
//
//        idContract = 1
//    }

}