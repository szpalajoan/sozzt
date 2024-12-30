package pl.jkap.sozzt.filestorage.domain

import org.springframework.mock.web.MockMultipartFile
import org.springframework.web.multipart.MultipartFile
import pl.jkap.sozzt.consents.domain.PlotOwnerConsentSample
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanSample
import pl.jkap.sozzt.routepreparation.RoutePreparationSample
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.filestorage.dto.AddFileDto
import pl.jkap.sozzt.filestorage.dto.FileDto

import java.nio.file.Files
import java.nio.file.Paths

import static pl.jkap.sozzt.filestorage.domain.FileType.*

trait FileSample implements ContractSample, PreliminaryPlanSample, RoutePreparationSample, PlotOwnerConsentSample {

    MultipartFile KRYNICA_CONTRACT_SCAN_FILE = new MockMultipartFile("KRYNICA_CONTRACT", "KRYNICA_CONTRACT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/contract/KRYNICA_CONTRACT.pdf")))
    MultipartFile KRYNICA_PRELIMINARY_MAP_FILE = new MockMultipartFile("KRYNICA_PRELIMINARY_MAP", "KRYNICA_PRELIMINARY_MAP.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/preliminary/KRYNICA_PRELIMINARY_MAP.pdf")))
    MultipartFile KRYNICA_GEODETIC_MAP_FILE = new MockMultipartFile("KRYNICA_GEODETIC_MAP", "KRYNICA_GEODETIC_MAP.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/routePreparation/KRYNICA_GEODETIC_MAP.pdf")))
    MultipartFile KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_FILE = new MockMultipartFile("KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT", "KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/consents/private_plot_owner_consent/KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT.pdf")))
    MultipartFile KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_FILE = new MockMultipartFile("KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT", "KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT.pdf", "application/pdf", Files.readAllBytes(Paths.get("src/test/resources/consents/public_plot_owner_consent/KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT.pdf")))


    FileDto KRYNICA_CONTRACT_SCAN_METADATA = FileDto.builder()
            .fileId(UUID.fromString("4fb55d25-7e7c-4bea-9c78-cb000f9b823a"))
            .fileName(KRYNICA_CONTRACT_SCAN_FILE.getOriginalFilename())
            .objectId(KRYNICA_CONTRACT.contractId)
            .fileType(CONTRACT_SCAN_FROM_TAURON)
            .build()

    FileDto KRYNICA_PRELIMINARY_MAP_METADATA = FileDto.builder()
            .fileId(UUID.fromString("782b56b1-bad7-4865-a1ec-d6af52e7548a"))
            .fileName(KRYNICA_PRELIMINARY_MAP_FILE.getOriginalFilename())
            .objectId(KRYNICA_PRELIMINARY_PLAN.preliminaryPlanId)
            .fileType(PRELIMINARY_MAP)
            .build()

    FileDto KRYNICA_GEODETIC_MAP_METADATA = FileDto.builder()
            .fileId(UUID.fromString("0f48c2dc-86f6-4cfd-a7d2-72e166dc85ca"))
            .fileName(KRYNICA_GEODETIC_MAP_FILE.getOriginalFilename())
            .objectId(KRYNICA_ROUTE_PREPARATION.routePreparationId)
            .fileType(GEODETIC_MAP)
            .build()

    FileDto KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_METADATA = FileDto.builder()
            .fileId(UUID.fromString("cb90d402-b200-4d19-bc50-5c92abdf1388"))
            .fileName(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_FILE.getOriginalFilename())
            .objectId(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT.privatePlotOwnerConsentId)
            .fileType(PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT)
            .build()

    FileDto KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_METADATA = FileDto.builder()
            .fileId(UUID.fromString("e5673fde-9c7e-4f32-8136-50dca10b852d"))
            .fileName(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_FILE.getOriginalFilename())
            .objectId(KRYNICA_PUBLIC_OWNER_CONSENT.publicPlotOwnerConsentId)
            .fileType(PUBLIC_OWNER_CONSENT_AGREEMENT)
            .build()

    PreparedFile KRYNICA_CONTRACT_SCAN = new PreparedFile(KRYNICA_CONTRACT_SCAN_METADATA, KRYNICA_CONTRACT_SCAN_FILE)
    PreparedFile KRYNICA_PRELIMINARY_MAP = new PreparedFile(KRYNICA_PRELIMINARY_MAP_METADATA, KRYNICA_PRELIMINARY_MAP_FILE)
    PreparedFile KRYNICA_GEODETIC_MAP = new PreparedFile(KRYNICA_GEODETIC_MAP_METADATA, KRYNICA_GEODETIC_MAP_FILE)
    PreparedFile KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_SCAN = new PreparedFile(KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_METADATA, KRYNICA_PRIVATE_PLOT_OWNER_CONSENT_AGREEMENT_FILE)
    PreparedFile KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_SCAN = new PreparedFile(KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_METADATA, KRYNICA_PUBLIC_OWNER_CONSENT_AGREEMENT_FILE)

    FileDto with(FileDto fileDto, Map<String, Object> properties) {
        return SampleModifier.with(FileDto.class, fileDto, properties)
    }

    AddFileDto toAddFileDto(FileDto fileDto, MultipartFile file, UUID objectId) {
        return AddFileDto
                .builder()
                .fileId(fileDto.getFileId())
                .objectId(objectId)
                .file(file)
                .build()
    }

}