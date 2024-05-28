package pl.jkap.sozzt.sample

import pl.jkap.sozzt.contract.domain.ContractConfiguration
import pl.jkap.sozzt.contract.domain.ContractDetailsSample
import pl.jkap.sozzt.contract.domain.ContractFacade
import pl.jkap.sozzt.contract.domain.ContractSample
import pl.jkap.sozzt.contract.domain.ContractStepCreator
import pl.jkap.sozzt.contract.domain.LocationSample
import pl.jkap.sozzt.contract.dto.ContractDto
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
        ContractSample, LocationSample, ContractDetailsSample,
        UserSample, InstantSamples {

    Collection<UUID> addedFileIds = []

    InstantProvider instantProvider = new InstantProvider()
    TerrainVisionFacade terrainVisionFacade = new TerrainVisionConfiguration().terrainVisionFacade(instantProvider)
    PreliminaryPlanFacade preliminaryPlanFacade = new PreliminaryPlanConfiguration().preliminaryPlanFacade()
    ContractStepCreator contractStepCreator = ContractStepCreator.builder()
            .preliminaryPlanFacade(preliminaryPlanFacade)
            .terrainVisionFacade(terrainVisionFacade)
            .build()
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(contractStepCreator, instantProvider)
    FileStorageFacade fileStorageFacade = new FileStorageConfigurator().fileStorageFacade(new FileEventPublisherStub(contractFacade, preliminaryPlanFacade))

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
        addContractScan(preparedFile, createContractDto)
        return contractFacade.finalizeIntroduction(createContractDto.contractId)
    }

    FileDto addContractScan(PreparedFile preparedFile, ContractDto contractDto) {
        FileDto addedFile = fileStorageFacade.addContractScan(toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, contractDto.contractId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    FileDto addPreliminaryMap(PreparedFile preparedFile, PreliminaryPlanDto preliminaryPlanDto) {
        FileDto addedFile = fileStorageFacade.addPreliminaryMap(toAddFileDto(preparedFile.metadata, preparedFile.fileAsMultipartFile, preliminaryPlanDto.preliminaryPlanId))
        addedFileIds.add(addedFile.fileId)
        return addedFile
    }

    void deleteFile(UUID fileId) {
        fileStorageFacade.deleteFile(fileId)
        addedFileIds.remove(fileId)
    }

}
