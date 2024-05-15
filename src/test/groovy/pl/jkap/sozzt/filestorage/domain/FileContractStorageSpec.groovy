package pl.jkap.sozzt.filestorage.domain

import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.filestorage.exception.FileNotFoundException
import pl.jkap.sozzt.sample.SozztSpecification

import java.nio.file.Files

class FileContractStorageSpec extends SozztSpecification {

    FileDto addedFileDto

    def cleanup(){
        if(addedFileDto != null){
            fileStorageFacade.deleteFile(addedFileDto.fileId)
        }
    }

    def "Should add a scan from Tauron to contract"(){
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        and: "there is a $KRYNICA_CONTRACT contract added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        when: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_CONTRACT_SCAN_FILE to $KRYNICA_CONTRACT contract"
            addedFileDto = fileStorageFacade.addContractScan(toAddContractScanFileDto(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT_SCAN_FILE, KRYNICA_CONTRACT))
        then: "$KRYNICA_CONTRACT_SCAN_FILE is added to $KRYNICA_CONTRACT contract"
            addedFileDto == KRYNICA_CONTRACT_SCAN
            Files.readAllBytes(fileStorageFacade.downloadFile(addedFileDto.getFileId())) == KRYNICA_CONTRACT_SCAN_FILE.getBytes()
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONTRACT, [isScanFromTauronUploaded : true])
    }


    def "Should delete a scan from Tauron from contract"(){
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        and: "there is a $KRYNICA_CONTRACT contract added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        and: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_CONTRACT_SCAN_FILE to $KRYNICA_CONTRACT contract"
            FileDto addedFileDto = fileStorageFacade.addContractScan(toAddContractScanFileDto(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT_SCAN_FILE, KRYNICA_CONTRACT))
        when: "$MONIKA_CONTRACT_INTRODUCER deletes $KRYNICA_CONTRACT_SCAN_FILE"
            fileStorageFacade.deleteFile(addedFileDto.fileId)
        then: "$KRYNICA_CONTRACT_SCAN_FILE is deleted"
            contractFacade.getContract(KRYNICA_CONTRACT.contractId) == with(KRYNICA_CONTRACT, [isScanFromTauronUploaded : false])
    }

    def "Should not be able to download deleted file"(){
        given: "$MONIKA_CONTRACT_INTRODUCER is logged in"
            loginUser(MONIKA_CONTRACT_INTRODUCER)
        and: "there is a $KRYNICA_CONTRACT contract added by $MONIKA_CONTRACT_INTRODUCER"
            contractFacade.addContract(toCreateContractDto(KRYNICA_CONTRACT))
        and: "$MONIKA_CONTRACT_INTRODUCER uploads $KRYNICA_CONTRACT_SCAN_FILE to $KRYNICA_CONTRACT contract"
            FileDto addedFileDto = fileStorageFacade.addContractScan(toAddContractScanFileDto(KRYNICA_CONTRACT_SCAN, KRYNICA_CONTRACT_SCAN_FILE, KRYNICA_CONTRACT))
        and: "$MONIKA_CONTRACT_INTRODUCER deletes $KRYNICA_CONTRACT_SCAN_FILE"
            fileStorageFacade.deleteFile(addedFileDto.fileId)
        when: "$MONIKA_CONTRACT_INTRODUCER download $KRYNICA_CONTRACT_SCAN_FILE"
            fileStorageFacade.downloadFile(addedFileDto.getFileId())
        then: "$KRYNICA_CONTRACT_SCAN_FILE is not available"
            thrown(FileNotFoundException)
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