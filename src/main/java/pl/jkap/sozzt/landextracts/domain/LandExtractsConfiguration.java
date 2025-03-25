package pl.jkap.sozzt.landextracts.domain;

import lombok.RequiredArgsConstructor;
import pl.jkap.sozzt.instant.InstantProvider;

@RequiredArgsConstructor
public class LandExtractsConfiguration {

    public LandExtractsFacade landExtractsFacade(InstantProvider instantProvider, LandExtractsEventPublisher landExtractsEventPublisher) {
        InMemoryLandExtractsRepository landExtractsRepository = new InMemoryLandExtractsRepository();
        return new LandExtractsFacade(landExtractsRepository, landExtractsEventPublisher, instantProvider);
    }
} 