package pl.jkap.sozzt.file.domain;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.config.application.ConsentFileSpringEventPublisher;
import pl.jkap.sozzt.config.application.ContractFileSpringEventPublisher;

@Configuration
public class FileConfiguration {

    @Bean
    FileFacade fileContractFacade(ApplicationEventPublisher applicationEventPublisher) {
        FileWrapper fileWrapper = new FileWrapperImp();
        ContractFileSpringEventPublisher contractFileSpringEventPublisher = new ContractFileSpringEventPublisher(applicationEventPublisher);
        ConsentFileSpringEventPublisher consentFileSpringEventPublisher = new ConsentFileSpringEventPublisher(applicationEventPublisher);
        return new FileFacade(new FileSystemStorage(fileWrapper), contractFileSpringEventPublisher,
                consentFileSpringEventPublisher);
    }
}
