package pl.jkap.sozzt.consent.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ConsentConfiguration {

    public ConsentFacade consentFacade() {
        ConsentRepository consentRepository = new InMemoryConsentRepository();
        ConsentCreator consentCreator = new ConsentCreator();
        return new ConsentFacade(consentRepository, consentCreator);
    }

    @Bean
    ConsentFacade consentFacade(ConsentRepository consentRepository) {
        ConsentCreator consentCreator = new ConsentCreator();
        return new ConsentFacade(consentRepository, consentCreator);
    }
}
