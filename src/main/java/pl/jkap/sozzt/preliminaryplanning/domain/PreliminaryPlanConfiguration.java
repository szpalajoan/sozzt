package pl.jkap.sozzt.preliminaryplanning.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PreliminaryPlanConfiguration {

    @Bean
    PreliminaryPlanFacade preliminaryPlanningFacade(PreliminaryPlanRepository preliminaryPlanningRepository) {
        return PreliminaryPlanFacade.builder()
                .preliminaryPlanningRepository(preliminaryPlanningRepository)
                .build();
    }

    PreliminaryPlanFacade preliminaryPlanFacade() {
        return preliminaryPlanningFacade(null);
    }
}
