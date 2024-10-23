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
import pl.jkap.sozzt.instant.InstantProvider
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanConfiguration
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanSample
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto
import pl.jkap.sozzt.terrainvision.TerrainVisionSample
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionConfiguration
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade
import pl.jkap.sozzt.user.UserSample
import spock.lang.Specification

class SozztSpecification extends Specification implements FileSample, PreliminaryPlanSample, TerrainVisionSample,
        ContractSample, LocationSample, ContractDetailsSample, ContractStepSample,
        UserSample, InstantSamples {
    Collection<UUID> addedFileIds = []

    InstantProvider instantProvider = new InstantProvider()
    ContractSecurityFacade contractSecurityFacade = new ContractSecurityConfiguration().contractSecurityFacade()
    TerrainVisionFacade terrainVisionFacade = new TerrainVisionConfiguration().terrainVisionFacade(instantProvider)
    PreliminaryPlanFacade preliminaryPlanFacade = new PreliminaryPlanConfiguration().preliminaryPlanFacade()
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(contractSecurityFacade, preliminaryPlanFacade, terrainVisionFacade, instantProvider)
    FileStorageFacade fileStorageFacade = new FileStorageConfigurator().fileStorageFacade(contractSecurityFacade, new FileEventPublisherStub(contractFacade, preliminaryPlanFacade))

    def setup() {
        instantProvider.useFixedClock(NOW)
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
        if(step >= ExpectedStageSample.COMPLETE_INTRODUCTION) {
            loginUser(MONIKA_CONTRACT_INTRODUCER)
            completeIntroduceContract(KRYNICA_CONTRACT)
        }
        if(step >= ExpectedStageSample.COMPLETE_PRELIMINARY_PLAN) {
            loginUser(DAREK_PRELIMINARY_PLANER)
            completePreliminaryPlan(KRYNICA_PRELIMINARY_PLAN)
        }
//        if(step >= ExpectedStepSample.COMPLETE_TERRAIN_VISION) {
//            terrainVisionFacade.addTerrainVision(contractDto.contractId)
//        }
    }

    private void completeIntroduceContract(ContractDto contractDto) {
        addCompletelyIntroduceContract(contractDto, KRYNICA_CONTRACT_SCAN)
    }

    private void completePreliminaryPlan(PreliminaryPlanDto preliminaryPlanDto) {
        uploadPreliminaryMap(preliminaryPlanDto, KRYNICA_PRELIMINARY_MAP)
        preliminaryPlanFacade.addGoogleMapUrl(preliminaryPlanDto.preliminaryPlanId, KRYNICA_GOOGLE_MAP_URL)
        contractFacade.finalizePreliminaryPlan(preliminaryPlanDto.preliminaryPlanId)
    }

}
