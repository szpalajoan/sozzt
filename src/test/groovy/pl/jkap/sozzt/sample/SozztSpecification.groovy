package pl.jkap.sozzt.sample

import pl.jkap.sozzt.contract.domain.ContractConfiguration
import pl.jkap.sozzt.contract.domain.ContractDetailsSample
import pl.jkap.sozzt.contract.domain.ContractFacade
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.contract.domain.ContractStepSample
import pl.jkap.sozzt.contract.domain.LocationSample
import pl.jkap.sozzt.contract.dto.ContractDto
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityConfiguration
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade
import pl.jkap.sozzt.filestorage.domain.FileEventPublisherStub
import pl.jkap.sozzt.filestorage.domain.FileSample
import pl.jkap.sozzt.filestorage.domain.FileStorageConfigurator
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade
import pl.jkap.sozzt.filestorage.domain.PreparedFile
import pl.jkap.sozzt.filestorage.dto.FileDto
import pl.jkap.sozzt.inmemory.InMemoryEventInvoker
import pl.jkap.sozzt.instant.InstantProvider
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanConfiguration
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanEventPublisherStub
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanSample
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationConfiguration
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationFacade
import pl.jkap.sozzt.terrainvision.TerrainVisionSample
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionConfiguration
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionEventPublisherStub
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto
import pl.jkap.sozzt.user.UserSample
import spock.lang.Specification

import static pl.jkap.sozzt.sample.ExpectedStageSample.*

class SozztSpecification extends Specification implements FileSample, PreliminaryPlanSample, TerrainVisionSample, RoutePreparationSample,
        ContractSample, LocationSample, ContractDetailsSample, ContractStepSample,
        UserSample, InstantSamples {
    Collection<UUID> addedFileIds = []

    InstantProvider instantProvider = new InstantProvider()
    InMemoryEventInvoker eventInvoker = new InMemoryEventInvoker()
    ContractSecurityFacade contractSecurityFacade = new ContractSecurityConfiguration().contractSecurityFacade()
    PreliminaryPlanFacade preliminaryPlanFacade = new PreliminaryPlanConfiguration().preliminaryPlanFacade(new PreliminaryPlanEventPublisherStub(eventInvoker))
    TerrainVisionFacade terrainVisionFacade = new TerrainVisionConfiguration().terrainVisionFacade(instantProvider, new TerrainVisionEventPublisherStub(eventInvoker))
    RoutePreparationFacade routePreparationFacade = new RoutePreparationConfiguration().routePreparationFacade()
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(contractSecurityFacade, preliminaryPlanFacade, terrainVisionFacade, routePreparationFacade, instantProvider)
    FileStorageFacade fileStorageFacade = new FileStorageConfigurator().fileStorageFacade(contractSecurityFacade, new FileEventPublisherStub(eventInvoker))

    def setup() {
        instantProvider.useFixedClock(NOW)
        eventInvoker.addFacades(contractFacade, preliminaryPlanFacade)
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
        if(step >= COMPLETED_TERRAIN_VISION) {
            loginUser(MARCIN_TERRAIN_VISIONER)
            if(step == COMPLETED_TERRAIN_VISION_WITHOUT_MAP_REQUIRED){
                completeTerrainVision(COMPLETED_KRYNICA_TERRAIN_VISION)
            } else {
                completeTerrainVision(COMPLETED_KRYNICA_TERRAIN_VISION, true)
            }

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
}
