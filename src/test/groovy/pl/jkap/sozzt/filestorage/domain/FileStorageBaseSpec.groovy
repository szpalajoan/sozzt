package pl.jkap.sozzt.filestorage.domain

import pl.jkap.sozzt.contract.domain.ContractConfiguration
import pl.jkap.sozzt.contract.domain.ContractFacade
import pl.jkap.sozzt.instant.InstantProvider
import pl.jkap.sozzt.sample.SampleSpecification

class FileStorageBaseSpec extends SampleSpecification{
    InstantProvider instantProvider = new InstantProvider()
    ContractFacade contractFacade = new ContractConfiguration().contractFacade(instantProvider)
    FileStorageFacade fileStorageFacade = new FileStorageConfigurator().fileStorageFacade(new FileContractSpringEventPublisherStub(contractFacade))
}
