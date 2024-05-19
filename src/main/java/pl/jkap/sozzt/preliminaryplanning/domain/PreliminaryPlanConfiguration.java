package pl.jkap.sozzt.preliminaryplanning.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreliminaryPlanConfiguration {

    @Bean
    PreliminaryPlanFacade preliminaryPlanningFacade(PreliminaryPlanRepository preliminaryPlanRepository) {
        return PreliminaryPlanFacade.builder()
                .preliminaryPlanningRepository(preliminaryPlanRepository)
                .build();
    }

    public PreliminaryPlanFacade preliminaryPlanFacade() {
        return preliminaryPlanningFacade(new InMemoryPreliminaryPlanRepository());
    }
}
