package pl.jkap.sozzt.consents.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class ConsentsConfiguration {

    @Bean
    public ConsentsFacade consentsFacade(FileStorageFacade fileStorageFacade,
                                         InstantProvider instantProvider,
                                         ConsentsEventPublisher consentsEventPublisher) {
        return ConsentsFacade.builder()
                .consentsRepository(new InMemoryConsentsRepository())
                .consentsEventPublisher(consentsEventPublisher)
                .fileStorageFacade(fileStorageFacade)
                .instantProvider(instantProvider)
                .build();
    }
}
