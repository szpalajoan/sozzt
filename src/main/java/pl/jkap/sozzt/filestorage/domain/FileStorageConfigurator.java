package pl.jkap.sozzt.filestorage.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade;

@Configuration
public class FileStorageConfigurator {

    @Bean
    public FileStorageFacade fileStorageFacade(ContractSecurityFacade contractSecurityFacade, FileEventPublisher fileEventPublisher) {
        return FileStorageFacade.builder()
                .contractSecurityFacade(contractSecurityFacade)
                .fileSystemStorage(new FileSystemStorage())
                .fileRepository(new InMemoryFileRepository())
                .fileEventPublisher(fileEventPublisher)
                .build();
    }

}
