package pl.jkap.sozzt.filestorage.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class FileStorageConfigurator {

    @Bean
    public FileStorageFacade fileStorageFacade(FileRepository fileRepository, FileEventPublisher fileEventPublisher) {
        return new FileStorageFacade(new FileSystemStorage(), fileRepository, fileEventPublisher);
    }

    public FileStorageFacade fileStorageFacade(FileEventPublisherStub contractSpringEventPublisher) {
        return fileStorageFacade(new InMemoryFileRepository(), contractSpringEventPublisher);
    }
}
