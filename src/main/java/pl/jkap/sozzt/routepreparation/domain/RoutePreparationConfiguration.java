package pl.jkap.sozzt.routepreparation.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class RoutePreparationConfiguration {

    @Bean
    public RoutePreparationFacade routePreparationFacade(RoutePreparationEventPublisher routePreparationEventPublisher,
                                                         FileStorageFacade fileStorageFacade, InstantProvider instantProvider) {
        return new RoutePreparationFacade(new InMemoryRoutePreparationRepository(), routePreparationEventPublisher,
                fileStorageFacade,
                instantProvider);
    }
}
