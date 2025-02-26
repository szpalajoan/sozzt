package pl.jkap.sozzt.filestorage.domain

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import pl.jkap.sozzt.consents.domain.PlotOwnerConsentSample
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanSample
import pl.jkap.sozzt.projectpurposesmappreparation.ProjectPurposesMapPreparationSample
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.filestorage.dto.AddFileDto
import pl.jkap.sozzt.filestorage.dto.FileDto

import java.nio.file.Files
import java.nio.file.Paths

import static pl.jkap.sozzt.filestorage.domain.FileType.*

trait FileSample implements ContractSample, PreliminaryPlanSample, ProjectPurposesMapPreparationSample, PlotOwnerConsentSample {

    MultipartFile KRYNICA_CONTRACT_SCAN_FILE = new MockMultipartFile("KRYNICA_CONTRACT", "KRYNICA_CONTRACT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/contract/KRYNICA_CONTRACT.pdf")))
    MultipartFile KRYNICA_PRELIMINARY_MAP_FILE = new MockMultipartFile("KRYNICA_PRELIMINARY_MAP", "KRYNICA_PRELIMINARY_MAP.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/preliminary/KRYNICA_PRELIMINARY_MAP.pdf")))
    MultipartFile KRYNICA_GEODETIC_MAP_FILE = new MockMultipartFile("KRYNICA_GEODETIC_MAP", "KRYNICA_GEODETIC_MAP.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/projectpurposesmappreparation/KRYNICA_GEODETIC_MAP.pdf")))
    MultipartFile KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_FILE = new MockMultipartFile("KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT", "KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/consents/private_plot_owner_consent/KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT.pdf")))
    MultipartFile KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_FILE = new MockMultipartFile("KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT", "KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/consents/public_plot_owner_consent/KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT.pdf")))
    MultipartFile KRYNICA_MAP_WITH_ROUTE_FILE = new MockMultipartFile("KRYNICA_MAP_WITH_ROUTE", "KRYNICA_MAP_WITH_ROUTE.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/documentation/mapWithRoute/KRYNICA_MAP_WITH_ROUTE.pdf")))
    MultipartFile KRYNICA_PDF_WITH_ROUTE_AND_DATA_FILE = new MockMultipartFile("KRYNICA_PDF_WITH_ROUTE_AND_DATA", "KRYNICA_PDF_WITH_ROUTE_AND_DATA.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/documentation/pdfWithRouteAndData/KRYNICA_PDF_WITH_ROUTE_AND_DATA.pdf")))
    MultipartFile KRYNICA_COMPILED_DOCUMENT_FILE = new MockMultipartFile("KRYNICA_COMPILED_DOCUMENT", "KRYNICA_COMPILED_DOCUMENT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/documentation/compiled/KRYNICA_COMPILED_DOCUMENT.pdf")))

    FileDto KRYNICA_CONTRACT_SCAN_METADATA = FileDto.builder()
            .fileId(UUID.fromString("4fb55d25-7e7c-4bea-9c78-cb000f9b823a"))
            .fileName(KRYNICA_CONTRACT_SCAN_FILE.getOriginalFilename())
            .contractId(KRYNICA_CONTRACT.contractId)
            .fileType(CONTRACT_SCAN_FROM_TAURON)
            .build()

    FileDto KRYNICA_PRELIMINARY_MAP_METADATA = FileDto.builder()
            .fileId(UUID.fromString("782b56b1-bad7-4865-a1ec-d6af52e7548a"))
            .fileName(KRYNICA_PRELIMINARY_MAP_FILE.getOriginalFilename())
            .contractId(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId)
            .fileType(PRELIMINARY_MAP)
            .build()

    FileDto KRYNICA_GEODETIC_MAP_METADATA = FileDto.builder()
            .fileId(UUID.fromString("0f48c2dc-86f6-4cfd-a7d2-72e166dc85ca"))
            .fileName(KRYNICA_GEODETIC_MAP_FILE.getOriginalFilename())
            .contractId(KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION.projectPurposesMapPreparationId)
            .fileType(GEODETIC_MAP)
            .build()

    FileDto KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_METADATA = FileDto.builder()
            .fileId(UUID.fromString("cb90d402-b200-4d19-bc50-5c92abdf1388"))
            .fileName(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_FILE.getOriginalFilename())
            .contractId(KRYNICA_CONTRACT.contractId)
            .additionalObjectId(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT.privatePlotOwnerConsentId)
            .fileType(PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT)
            .build()

    FileDto KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_METADATA = FileDto.builder()
            .fileId(UUID.fromString("e5673fde-9c7e-4f32-8136-50dca10b852d"))
            .fileName(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_FILE.getOriginalFilename())
            .contractId(KRYNICA_CONTRACT.contractId)
            .additionalObjectId(KRYNICA_PUBLIC_OWNER_CONSENT.publicPlotOwnerConsentId)
            .fileType(PUBLIC_OWNER_CONSENT_AGREEMENT)
            .build()

    FileDto KRYNICA_MAP_WITH_ROUTE_METADATA = FileDto.builder()
            .fileId(UUID.fromString("d5fd3f0f-fef5-4881-8156-e333911d13ef"))
            .fileName(KRYNICA_MAP_WITH_ROUTE_FILE.getOriginalFilename())
            .contractId(KRYNICA_CONTRACT.contractId)
            .fileType(MAP_WITH_ROUTE)
            .build()

    FileDto KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA = FileDto.builder()
            .fileId(UUID.fromString("bd55b31f-9d38-4f9f-bbb5-db9a16e16a72"))
            .fileName(KRYNICA_PDF_WITH_ROUTE_AND_DATA_FILE.getOriginalFilename())
            .contractId(KRYNICA_CONTRACT.contractId)
            .fileType(PDF_WITH_ROUTE_AND_DATA)
            .build()

    FileDto KRYNICA_COMPILED_DOCUMENT_METADATA = FileDto.builder()
            .fileId(UUID.fromString("6362b0aa-1f15-4355-95a4-9a49392b9f58"))
            .fileName(KRYNICA_COMPILED_DOCUMENT_FILE.getOriginalFilename())
            .contractId(KRYNICA_CONTRACT.contractId)
            .fileType(COMPILED_DOCUMENT)
            .build()

    PreparedFile KRYNICA_CONTRACT_SCAN = new PreparedFile(KRYNICA_CONTRACT_SCAN_METADATA, KRYNICA_CONTRACT_SCAN_FILE)
    PreparedFile KRYNICA_PRELIMINARY_MAP = new PreparedFile(KRYNICA_PRELIMINARY_MAP_METADATA, KRYNICA_PRELIMINARY_MAP_FILE)
    PreparedFile KRYNICA_GEODETIC_MAP = new PreparedFile(KRYNICA_GEODETIC_MAP_METADATA, KRYNICA_GEODETIC_MAP_FILE)
    PreparedFile KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_SCAN = new PreparedFile(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_METADATA, KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_FILE)
    PreparedFile KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_SCAN = new PreparedFile(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_METADATA, KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_FILE)
    PreparedFile KRYNICA_MAP_WITH_ROUTE = new PreparedFile(KRYNICA_MAP_WITH_ROUTE_METADATA, KRYNICA_MAP_WITH_ROUTE_FILE)
    PreparedFile KRYNICA_PDF_WITH_ROUTE_AND_DATA = new PreparedFile(KRYNICA_PDF_WITH_ROUTE_AND_DATA_METADATA, KRYNICA_PDF_WITH_ROUTE_AND_DATA_FILE)
    PreparedFile KRYNICA_COMPILED_DOCUMENT = new PreparedFile(KRYNICA_COMPILED_DOCUMENT_METADATA, KRYNICA_COMPILED_DOCUMENT_FILE)

    FileDto with(FileDto fileDto, Map<String, Object> properties) {
        return SampleModifier.with(FileDto.class, fileDto, properties)
    }

    AddFileDto toAddFileDto(FileDto fileDto, MultipartFile file, UUID contractId, UUID additionalObjectId = null) {
        return AddFileDto
                .builder()
                .fileId(fileDto.getFileId())
                .contractId(contractId)
                .additionalObjectId(fileDto.getAdditionalObjectId())
                .file(file)
                .build()
    }

}