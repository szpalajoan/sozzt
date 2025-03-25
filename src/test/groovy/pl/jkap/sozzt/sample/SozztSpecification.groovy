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
import pl.jkap.sozzt.documentation.domain.DocumentationEventPublisherStub
import pl.jkap.sozzt.documentation.domain.DocumentationFacade
import pl.jkap.sozzt.documentation.domain.DocumentationSample
import pl.jkap.sozzt.landextracts.domain.LandExtractsConfiguration
import pl.jkap.sozzt.landextracts.domain.LandExtractsEventPublisherStub
import pl.jkap.sozzt.landextracts.domain.LandExtractsSample
import pl.jkap.sozzt.landextracts.domain.LandExtractsFacade
import pl.jkap.sozzt.landextracts.dto.LandExtractsDto
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationConfiguration
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationEventPublisherStub
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationFacade
import pl.jkap.sozzt.routepreparation.RouteDrawingSample
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
import pl.jkap.sozzt.remark.domain.RemarkConfiguration
import pl.jkap.sozzt.remark.domain.RemarkFacade
import pl.jkap.sozzt.remark.domain.RemarkSample
import pl.jkap.sozzt.projectpurposesmappreparation.ProjectPurposesMapPreparationSample
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationConfiguration
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationEventPublisherStub
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto
import pl.jkap.sozzt.routepreparation.RoutePreparationSample
import pl.jkap.sozzt.terrainvision.TerrainVisionSample
import pl.jkap.sozzt.terrainvision.domain.ProjectPurposesMapPreparationNeed
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionConfiguration
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionEventPublisherStub
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto
import pl.jkap.sozzt.user.UserSample
import spock.lang.Specification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*
import static pl.jkap.sozzt.terrainvision.domain.ProjectPurposesMapPreparationNeed.*

class SozztSpecification extends Specification implements FileSample, RemarkSample, TermVerificationSample,
        DocumentationSample, ConsentsSample, PlotOwnerConsentSample,
        PreliminaryPlanSample, TerrainVisionSample, ProjectPurposesMapPreparationSample, LandExtractsSample, RoutePreparationSample,
        ContractSample, LocationSample, ContractDetailsSample, ContractStepSample,
        RouteDrawingSample, UserSample, InstantSamples {
    Collection<UUID> addedFileIds = []

    InstantProvider instantProvider = new InstantProvider()
    InMemoryEventInvoker eventInvoker = new InMemoryEventInvoker()
    ContractSecurityFacade contractSecurityFacade = new ContractSecurityConfiguration().contractSecurityFacade()
    PreliminaryPlanFacade preliminaryPlanFacade = new PreliminaryPlanConfiguration().preliminaryPlanFacade(new PreliminaryPlanEventPublisherStub(eventInvoker))
    TerrainVisionFacade terrainVisionFacade = new TerrainVisionConfiguration().terrainVisionFacade(instantProvider, new TerrainVisionEventPublisherStub(eventInvoker))
    FileStorageFacade fileStorageFacade = new FileStorageConfigurator().fileStorageFacade(contractSecurityFacade, new FileEventPublisherStub(eventInvoker))
    ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade = new ProjectPurposesMapPreparationConfiguration().projectPurposesMapPreparationFacade(new ProjectPurposesMapPreparationEventPublisherStub(eventInvoker), fileStorageFacade, instantProvider)
    RoutePreparationFacade routePreparationFacade = new RoutePreparationConfiguration().routePreparationFacade(new RoutePreparationEventPublisherStub(eventInvoker), fileStorageFacade, instantProvider)
    ConsentsFacade consentsFacade = new ConsentsConfiguration().consentsFacade(fileStorageFacade, instantProvider, new ConsentsEventPublisherStub(eventInvoker))
    DocumentationFacade documentationFacade = new DocumentationConfiguration().documentationFacade(new DocumentationEventPublisherStub(eventInvoker), fileStorageFacade)
    LandExtractsFacade landExtractsFacade = new LandExtractsConfiguration().landExtractsFacade(instantProvider, new LandExtractsEventPublisherStub(eventInvoker))
    RemarkFacade remarkFacade = new RemarkConfiguration().remarkFacade(instantProvider)
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(contractSecurityFacade, preliminaryPlanFacade, terrainVisionFacade,
            projectPurposesMapPreparationFacade, routePreparationFacade, landExtractsFacade, consentsFacade, documentationFacade, remarkFacade,
            instantProvider)


    def setup() {
        instantProvider.useFixedClock(NOW)
        eventInvoker.addFacades(contractFacade,
                preliminaryPlanFacade,
                projectPurposesMapPreparationFacade)
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

    FileDto uploadGeodeticMap(ProjectPurposesMapPreparationDto projectPurposesMapPreparationDto, PreparedFile preparedFile ) {
        FileDto addedFile = projectPurposesMapPreparationFacade.addGeodeticMap(toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, projectPurposesMapPreparationDto.projectPurposesMapPreparationId))
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
        FileDto addedFile = routePreparationFacade.uploadDrawnRoute(documentationId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, documentationId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto uploadPdfWithRouteAndData(PreparedFile preparedFile, UUID documentationId) {
        FileDto addedFile = routePreparationFacade.uploadPdfWithRouteAndData(documentationId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, documentationId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto uploadCompiledDocument(PreparedFile preparedFile, UUID documentationId) {
        FileDto addedFile = documentationFacade.uploadCompiledDocument(documentationId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, documentationId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto addZudConsentAgreement(PreparedFile preparedFile, UUID consentId) {
        FileDto addedFile = consentsFacade.addZudConsentAgreement(consentId, toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, consentId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    void deleteFile(UUID fileId) {
        fileStorageFacade.deleteFile(fileId)
        addedFileIds.remove(fileId)
    }

    void addKrynicaContractOnStage(ExpectedStageSample step, ContractFixture contractFixture = new ContractFixture()) {
        if(step >= COMPLETED_INTRODUCTION || step >= BEGIN_PRELIMINARY_PLAN) {
            loginUser(MONIKA_CONTRACT_INTRODUCER)
            completeIntroduceContract(with(KRYNICA_CONTRACT, [zudConsentRequired : contractFixture.isZudRequired]))
        }
        if(step >= COMPLETED_PRELIMINARY_PLAN || step >= BEGIN_TERRAIN_VISION) {
            loginUser(DAREK_PRELIMINARY_PLANER)
            completePreliminaryPlan(COMPLETED_KRYNICA_PRELIMINARY_PLAN)
        }
        if(step >= COMPLETED_TERRAIN_VISION || step >= BEGIN_PROJECT_PURPOSES_MAP_PREPARATION || step >= BEGIN_LAND_EXTRACTS) {
            loginUser(MARCIN_TERRAIN_VISIONER)
            completeTerrainVision(COMPLETED_KRYNICA_TERRAIN_VISION, contractFixture.isMapRequired)
        }
        if(step >= COMPLETED_LAND_EXTRACTS){
            loginUser(KASIA_CONSENT_CORDINATOR)
            completeLandExtracts(COMPLETED_KRYNICA_LAND_EXTRACTS)
        }
        
        if(step >= COMPLETED_PROJECT_PURPOSES_MAP_PREPARATION) {
            loginUser(WALDEK_SURVEYOR)
            completeProjectPurposesMapPreparation(COMPLETED_KRYNICA_PROJECT_PURPOSE_MAP_PREPARATION, contractFixture.isMapRequired)
        }
        if(step >= COMPLETED_ROUTE_PREPARATION) {
            loginUser(DANIEL_ROUTE_DRAWER)
            completeRoutePreparation(COMPLETED_KRYNICA_ROUTE_PREPARATION)
        }
        if(step >= COMPLETED_CONSENTS_COLLECTION || step >= BEGIN_DOCUMENTATION) {
            loginUser(KASIA_CONSENT_CORDINATOR)
            completeConsentsCollection(COMPLETED_KRYNICA_CONSENTS, contractFixture.isZudRequired)
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

    private void completeTerrainVision(TerrainVisionDto terrainVisionDto, boolean withProjectPurposesMapPreparation = false) {
        ProjectPurposesMapPreparationNeed projectPurposesMapPreparationNeed = withProjectPurposesMapPreparation ? NECESSARY : NOT_NEED
        terrainVisionFacade.confirmAllPhotosAreUploaded(terrainVisionDto.terrainVisionId)
        terrainVisionFacade.setProjectPurposesMapPreparationNeed(terrainVisionDto.terrainVisionId, projectPurposesMapPreparationNeed)
        terrainVisionFacade.completeTerrainVision(terrainVisionDto.terrainVisionId)
    }

    void completeLandExtracts(LandExtractsDto landExtractsDto) {
        landExtractsFacade.requestForLandExtractsSent(KRYNICA_CONTRACT.contractId)
        landExtractsFacade.completeLandExtracts(landExtractsDto.landExtractsId)
    }

    void completeProjectPurposesMapPreparation(ProjectPurposesMapPreparationDto projectPurposesMapPreparation, boolean isMapRequired = true) {
        if(!isMapRequired) {
            return
        }
        uploadGeodeticMap(projectPurposesMapPreparation, KRYNICA_GEODETIC_MAP)
        loginUser(DANIEL_ROUTE_DRAWER)
        projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(projectPurposesMapPreparation.projectPurposesMapPreparationId)
    }

    void completeRoutePreparation(RoutePreparationDto routePreparationDto) {
        loginUser(DANIEL_ROUTE_DRAWER)
        routePreparationFacade.approveCorrectnessOfTheMap(routePreparationDto.routePreparationId)
        routePreparationFacade.choosePersonResponsibleForRouteDrawing(routePreparationDto.routePreparationId, DANIEL_ROUTE_DRAWER.name)
        uploadRouteDrawing(KRYNICA_MAP_WITH_ROUTE, routePreparationDto.routePreparationId)
        uploadPdfWithRouteAndData(KRYNICA_PDF_WITH_ROUTE_AND_DATA, routePreparationDto.routePreparationId)
        routePreparationFacade.completeRoutePreparation(routePreparationDto.routePreparationId)
    }

    void completeConsentsCollection(ConsentsDto consentsDto, boolean isZudRequired = true) {
        if(isZudRequired) {
            consentsFacade.updateZudConsent(KRYNICA_CONTRACT.contractId,
                    toUpdateZudConsent(KRYNICA_ZUD_CONSENT))
            addZudConsentAgreement(KRYNICA_ZUD_CONSENT_AGREEMENT_SCAN, KRYNICA_CONTRACT.contractId)
        }
        consentsFacade.completeConsentsCollection(consentsDto.consentId)
    }


}
