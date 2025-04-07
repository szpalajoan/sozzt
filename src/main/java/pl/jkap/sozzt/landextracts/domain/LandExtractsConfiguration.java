package pl.jkap.sozzt.landextracts.domain;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
@RequiredArgsConstructor
public class LandExtractsConfiguration {

    @Bean
    public LandExtractsFacade landExtractsFacade(InstantProvider instantProvider, LandExtractsEventPublisher landExtractsEventPublisher) {
        InMemoryLandExtractsRepository landExtractsRepository = new InMemoryLandExtractsRepository();
        return new LandExtractsFacade(landExtractsRepository, landExtractsEventPublisher, instantProvider);
    }
} 