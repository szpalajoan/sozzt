package pl.jkap.sozzt.filestorage.domain

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanSample
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.filestorage.dto.AddFileDto
import pl.jkap.sozzt.filestorage.dto.FileDto

import java.nio.file.Files
import java.nio.file.Paths

trait FileSample implements ContractSample, PreliminaryPlanSample {


    MultipartFile KRYNICA_CONTRACT_SCAN_FILE = new MockMultipartFile("KRYNICA_CONTRACT", "KRYNICA_CONTRACT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/contract/KRYNICA_CONTRACT.pdf")))
    MultipartFile KRYNICA_PRELIMINARY_MAP_FILE = new MockMultipartFile("KRYNICA_PRELIMINARY_MAP", "KRYNICA_PRELIMINARY_MAP.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/preliminary/KRYNICA_PRELIMINARY_MAP.pdf")))


    FileDto KRYNICA_CONTRACT_SCAN = FileDto.builder()
            .fileId(UUID.fromString("4fb55d25-7e7c-4bea-9c78-cb000f9b823a"))
            .fileName(KRYNICA_CONTRACT_SCAN_FILE.getOriginalFilename())
            .objectId(KRYNICA_CONTRACT.contractId)
            .fileType(FileDto.FileTypeDto.CONTRACT_SCAN_FROM_TAURON)
            .build()

    FileDto KRYNICA_PRELIMINARY_MAP = FileDto.builder()
            .fileId(UUID.fromString("782b56b1-bad7-4865-a1ec-d6af52e7548a"))
            .fileName(KRYNICA_PRELIMINARY_MAP_FILE.getOriginalFilename())
            .objectId(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId)
            .fileType(FileDto.FileTypeDto.PRELIMINARY_MAP)
            .build()

    FileDto with(FileDto fileDto, Map<String, Object> properties) {
        return SampleModifier.with(FileDto.class, fileDto, properties)
    }

    AddFileDto toAddContractScanFileDto(FileDto fileDto, MultipartFile file, ContractDto contractDto) {
        return AddFileDto
                .builder()
                .fileId(fileDto.getFileId())
                .objectId(contractDto.getContractId())
                .file(file)
                .build()
    }
}