package pl.jkap.sozzt.filestorage.domain

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.filestorage.dto.AddContractScanFileDto
import pl.jkap.sozzt.filestorage.dto.FileDto

import java.nio.file.Files
import java.nio.file.Paths

trait FileSample {


    MultipartFile KRYNICA_CONTRACT_SCAN_FILE = new MockMultipartFile("KRYNICA_CONTRACT", "KRYNICA_CONTRACT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/contract/KRYNICA_CONTRACT.pdf")))


    FileDto KRYNICA_CONTRACT_SCAN = FileDto.builder()
            .fileId(UUID.fromString("4fb55d25-7e7c-4bea-9c78-cb000f9b823a"))
            .fileName(KRYNICA_CONTRACT_SCAN_FILE.getOriginalFilename())
            .build()

    FileDto with(FileDto fileDto, Map<String, Object> properties) {
        return SampleModifier.with(FileDto.class, fileDto, properties)
    }

    AddContractScanFileDto toAddContractScanFileDto(FileDto fileDto, MultipartFile file, ContractDto contractDto) {
        return AddContractScanFileDto
                .builder()
                .fileId(fileDto.getFileId())
                .contractId(contractDto.getContractId())
                .file(file)
                .build()
    }
}