package pl.jkap.sozzt.filestorage.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade;

@Configuration
public class FileStorageConfigurator {

    @Bean
    FileStorageFacade fileStorageFacade(ContractSecurityFacade contractSecurityFacade, FileRepository fileRepository, FileEventPublisher fileEventPublisher) {
        return FileStorageFacade.builder()
                .contractSecurityFacade(contractSecurityFacade)
                .fileSystemStorage(new FileSystemStorage())
                .fileRepository(new InMemoryFileRepository())
                .fileEventPublisher(fileEventPublisher)
                .build();
    }

    public FileStorageFacade fileStorageFacade(ContractSecurityFacade contractSecurityFacade, FileEventPublisherStub contractSpringEventPublisher) {
        return fileStorageFacade(contractSecurityFacade, new InMemoryFileRepository(), contractSpringEventPublisher);
    }
}
