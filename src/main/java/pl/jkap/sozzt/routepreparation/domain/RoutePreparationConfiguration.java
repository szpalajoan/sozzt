package pl.jkap.sozzt.routepreparation.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RoutePreparationConfiguration {

    @Bean
    public RoutePreparationFacade routePreparationFacade() {
        return new RoutePreparationFacade(new InMemoryRoutePreparationRepository());
    }
}
