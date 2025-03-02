package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ProjectPurposesMapPreparationConfiguration {

    @Bean
    public ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade(ProjectPurposesMapPreparationEventPublisher projectPurposesMapPreparationEventPublisher) {
        return new ProjectPurposesMapPreparationFacade(new InMemoryProjectPurposesMapPreparationRepository(), projectPurposesMapPreparationEventPublisher);
    }
}
