package pl.jkap.sozzt.documentation.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class DocumentationConfiguration {
    @Bean
    public DocumentationFacade documentationFacade(FileStorageFacade fileStorageFacade, InstantProvider instantProvider) {
        return new DocumentationFacade(new InMemoryDocumentationRepository(), fileStorageFacade, instantProvider);
    }
}
