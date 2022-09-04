package pl.jkap.sozzt.consent.domain;

import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.config.application.ConsentSpringEventPublisher;

@Configuration
public class ConsentConfiguration {

    public ConsentFacade consentFacade(ApplicationEventPublisher applicationEventPublisher) {
        ConsentRepository consentRepository = new InMemoryConsentRepository();
        return consentFacade(applicationEventPublisher, consentRepository);
    }

    @Bean
    ConsentFacade consentFacade(ApplicationEventPublisher applicationEventPublisher, ConsentRepository consentRepository) {
        ConsentCreator consentCreator = new ConsentCreator();
        ConsentSpringEventPublisher consentSpringEventPublisher = new ConsentSpringEventPublisher(applicationEventPublisher);
        return new ConsentFacade(consentRepository, consentCreator, consentSpringEventPublisher);
    }
}
