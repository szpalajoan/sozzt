package pl.jkap.sozzt.preliminaryplanning.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class  PreliminaryPlanConfiguration {

    @Bean
    public PreliminaryPlanFacade preliminaryPlanFacade(PreliminaryPlanEventPublisher preliminaryPlanEventPublisher) {
        return PreliminaryPlanFacade.builder()
                .preliminaryPlanRepository(new InMemoryPreliminaryPlanRepository())
                .preliminaryPlanEventPublisher(preliminaryPlanEventPublisher)
                .build();
    }

//    public PreliminaryPlanFacade preliminaryPlanFacade() {
//        return preliminaryPlanningFacade(new InMemoryPreliminaryPlanRepository());
//    }
}
