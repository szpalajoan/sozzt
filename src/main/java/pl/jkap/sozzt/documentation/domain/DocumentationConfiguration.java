package pl.jkap.sozzt.documentation.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class DocumentationConfiguration {
    @Bean
    public DocumentationFacade documentationFacade(DocumentationEventPublisher documentationEventPublisher, FileStorageFacade fileStorageFacade) {
        return DocumentationFacade.builder()
                .documentationRepository(new InMemoryDocumentationRepository())
                .fileStorageFacade(fileStorageFacade)
                .documentationEventPublisher(documentationEventPublisher)
                .build();
    }
}
