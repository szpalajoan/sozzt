package pl.jkap.sozzt.filestorage.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfigurator {

    @Bean
    public FileStorageFacade fileStorageFacade(FileRepository fileRepository, FileContractSpringEventPublisher fileContractSpringEventPublisher) {
        return new FileStorageFacade(new FileSystemStorage(), fileRepository, fileContractSpringEventPublisher);
    }

    public FileStorageFacade fileStorageFacade(FileContractSpringEventPublisherStub contractSpringEventPublisher) {
        return fileStorageFacade(new InMemoryFileRepository(), contractSpringEventPublisher);
    }
}
