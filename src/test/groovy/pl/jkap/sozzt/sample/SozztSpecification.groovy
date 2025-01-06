package pl.jkap.sozzt.sample

import pl.jkap.sozzt.consents.domain.ConsentsConfiguration
import pl.jkap.sozzt.consents.domain.ConsentsEventPublisherStub
import pl.jkap.sozzt.consents.domain.ConsentsFacade
import pl.jkap.sozzt.consents.domain.ConsentsSample
import pl.jkap.sozzt.consents.domain.PlotOwnerConsentSample
import pl.jkap.sozzt.consents.dto.ConsentsDto
import pl.jkap.sozzt.contract.domain.*
import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityConfiguration
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade
import pl.jkap.sozzt.documentation.domain.DocumentationConfiguration
import pl.jkap.sozzt.documentation.domain.DocumentationFacade
import pl.jkap.sozzt.documentation.domain.DocumentationSample
import pl.jkap.sozzt.documentation.domain.RouteDrawingSample
import pl.jkap.sozzt.documentation.domain.TermVerificationSample
import pl.jkap.sozzt.filestorage.domain.*
import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.inmemory.InMemoryEventInvoker
import pl.jkap.sozzt.instant.InstantProvider
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanConfiguration
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanEventPublisherStub
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanSample
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto
import pl.jkap.sozzt.routepreparation.RoutePreparationSample
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationConfiguration
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationEventPublisherStub
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationFacade
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto
import pl.jkap.sozzt.terrainvision.TerrainVisionSample
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionConfiguration
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionEventPublisherStub
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto
import pl.jkap.sozzt.user.UserSample
import spock.lang.Specification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class SozztSpecification extends Specification implements FileSample, TermVerificationSample, RouteDrawingSample, DocumentationSample, ConsentsSample, PlotOwnerConsentSample, PreliminaryPlanSample, TerrainVisionSample, RoutePreparationSample,
        ContractSample, LocationSample, ContractDetailsSample, ContractStepSample,
        UserSample, InstantSamples {
    Collection<UUID> addedFileIds = []

    InstantProvider instantProvider = new InstantProvider()
    InMemoryEventInvoker eventInvoker = new InMemoryEventInvoker()
    ContractSecurityFacade contractSecurityFacade = new ContractSecurityConfiguration().contractSecurityFacade()
    PreliminaryPlanFacade preliminaryPlanFacade = new PreliminaryPlanConfiguration().preliminaryPlanFacade(new PreliminaryPlanEventPublisherStub(eventInvoker))
    TerrainVisionFacade terrainVisionFacade = new TerrainVisionConfiguration().terrainVisionFacade(instantProvider, new TerrainVisionEventPublisherStub(eventInvoker))
    RoutePreparationFacade routePreparationFacade = new RoutePreparationConfiguration().routePreparationFacade(new RoutePreparationEventPublisherStub(eventInvoker))
    FileStorageFacade fileStorageFacade = new FileStorageConfigurator().fileStorageFacade(contractSecurityFacade, new FileEventPublisherStub(eventInvoker))
    ConsentsFacade consentsFacade = new ConsentsConfiguration().consentsFacade(fileStorageFacade, instantProvider, new ConsentsEventPublisherStub(eventInvoker))
    DocumentationFacade documentationFacade = new DocumentationConfiguration().documentationFacade(fileStorageFacade,instantProvider)
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(contractSecurityFacade, preliminaryPlanFacade, terrainVisionFacade,
            routePreparationFacade, consentsFacade, documentationFacade,
            instantProvider)


    def setup() {
        instantProvider.useFixedClock(NOW)
        eventInvoker.addFacades(contractFacade,
                preliminaryPlanFacade,
                routePreparationFacade)
    }

    def cleanup(){
        addedFileIds.each { UUID addedFileId ->
            fileStorageFacade.deleteFile(addedFileId)
        }
    }



    ContractDto addCompletelyIntroduceContract(ContractDto createContractDto, PreparedFile preparedFile) {
        contractFacade.addContract(toCreateContractDto(createContractDto))
        addContractScan(preparedFile, createContractDto.contractId)
        return contractFacade.finalizeContractIntroduction(createContractDto.contractId)
    }

    FileDto addContractScan(PreparedFile preparedFile, UUID contractId) {
        FileDto addedFile = fileStorageFacade.addContractScan(toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, contractId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto uploadPreliminaryMap(PreliminaryPlanDto preliminaryPlanDto, PreparedFile preparedFile ) {
        FileDto addedFile = fileStorageFacade.addPreliminaryMap(toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, preliminaryPlanDto.preliminaryPlanId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto uploadGeodeticMap(RoutePreparationDto routePreparationDto, PreparedFile preparedFile ) {
        FileDto addedFile = fileStorageFacade.addGeodeticMap(toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, routePreparationDto.routePreparationId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto addPrivatePlotOwnerConsentAgreement(PreparedFile preparedFile, UUID consentId, UUID privatePlotOwnerConsentId) {
        FileDto addedFile = consentsFacade.addPrivatePlotOwnerConsentAgreement(consentId, privatePlotOwnerConsentId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, consentId, privatePlotOwnerConsentId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto addPublicOwnerConsentAgreement(PreparedFile preparedFile, UUID consentId, UUID publicOwnerConsentId) {
        FileDto addedFile = consentsFacade.addPublicOwnerConsentAgreement(consentId, publicOwnerConsentId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, consentId, publicOwnerConsentId))
        addedFileIds.add(addedFile.fileId)
        return addedFile

    }

    FileDto uploadRouteDrawing(PreparedFile preparedFile, UUID documentationId) {
        FileDto addedFile = documentationFacade.uploadDrawnRoute(documentationId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, documentationId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto uploadPdfWithRouteAndData(PreparedFile preparedFile, UUID documentationId) {
        FileDto addedFile = documentationFacade.uploadPdfWithRouteAndData(documentationId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, documentationId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto uploadCompiledDocument(PreparedFile preparedFile, UUID documentationId) {
        FileDto addedFile = documentationFacade.uploadCompiledDocument(documentationId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, documentationId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    void deleteFile(UUID fileId) {
        fileStorageFacade.deleteFile(fileId)
        addedFileIds.remove(fileId)
    }

    void addKrynicaContractOnStage(ExpectedStageSample step) {
        if(step >= COMPLETED_INTRODUCTION || step >= BEGIN_PRELIMINARY_PLAN) {
            loginUser(MONIKA_CONTRACT_INTRODUCER)
            completeIntroduceContract(KRYNICA_CONTRACT)
        }
        if(step >= COMPLETED_PRELIMINARY_PLAN || step >= BEGIN_TERRAIN_VISION) {
            loginUser(DAREK_PRELIMINARY_PLANER)
            completePreliminaryPlan(COMPLETED_KRYNICA_PRELIMINARY_PLAN)
        }
        if(step >= COMPLETED_TERRAIN_VISION || step >= BEGIN_ROUTE_PREPARATION || step >= BEGIN_CONSENTS_COLLECTION) {
            loginUser(MARCIN_TERRAIN_VISIONER)
            if(step == COMPLETED_TERRAIN_VISION_WITHOUT_MAP_REQUIRED){
                completeTerrainVision(COMPLETED_KRYNICA_TERRAIN_VISION)
            } else {
                completeTerrainVision(COMPLETED_KRYNICA_TERRAIN_VISION, true)
            }

        }
        if(step >= COMPLETED_ROUTE_PREPARATION) {
            loginUser(WALDEK_SURVEYOR)
            completeRoutePreparation(COMPLETED_KRYNICA_ROUTE_PREPARATION)
        }
        if(step >= COMPLETED_CONSENTS_COLLECTION || step >= BEGIN_DOCUMENTATION) {
            loginUser(KASIA_CONSENT_CORDINATOR)
            completeConsentsCollection(COMPLETED_KRYNICA_CONSENTS)
        }
    }

    private void completeIntroduceContract(ContractDto contractDto) {
        addCompletelyIntroduceContract(contractDto, KRYNICA_CONTRACT_SCAN)
    }

    private void completePreliminaryPlan(PreliminaryPlanDto preliminaryPlanDto) {
        uploadPreliminaryMap(preliminaryPlanDto, KRYNICA_PRELIMINARY_MAP)
        preliminaryPlanFacade.addGoogleMapUrl(preliminaryPlanDto.preliminaryPlanId, KRYNICA_GOOGLE_MAP_URL)
        preliminaryPlanFacade.completePreliminaryPlan(preliminaryPlanDto.preliminaryPlanId)
    }

    private void completeTerrainVision(TerrainVisionDto terrainVisionDto, boolean withMapChange = false) {
        TerrainVisionDto.MapChange mapChange = withMapChange ? TerrainVisionDto.MapChange.MODIFIED : TerrainVisionDto.MapChange.NOT_NECESSARY
        terrainVisionFacade.confirmAllPhotosAreUploaded(terrainVisionDto.terrainVisionId)
        terrainVisionFacade.confirmChangesOnMap(terrainVisionDto.terrainVisionId, mapChange)
        terrainVisionFacade.completeTerrainVision(terrainVisionDto.terrainVisionId)
    }

    void completeRoutePreparation(RoutePreparationDto routePreparationDto) {
        uploadGeodeticMap(routePreparationDto, KRYNICA_GEODETIC_MAP)
        routePreparationFacade.completeRoutePreparation(routePreparationDto.routePreparationId)
    }

    void completeConsentsCollection(ConsentsDto consentsDto) {
        consentsFacade.completeConsentsCollection(consentsDto.consentId)
    }
}
