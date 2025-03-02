package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class ProjectPurposesMapPreparationConfiguration {

    @Bean
    public ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade(ProjectPurposesMapPreparationEventPublisher projectPurposesMapPreparationEventPublisher,
                                                                                   FileStorageFacade fileStorageFacade,
                                                                                   InstantProvider instantProvider) {
        return new ProjectPurposesMapPreparationFacade(new InMemoryProjectPurposesMapPreparationRepository(),
                projectPurposesMapPreparationEventPublisher,
                fileStorageFacade,
                instantProvider);
    }
}
