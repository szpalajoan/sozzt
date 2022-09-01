package pl.jkap.sozzt.file.domain;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.config.application.ContractSpringEventPublisher;

@Configuration
public class FileConfiguration {

    @Bean
    FileFacade fileContractFacade(ApplicationEventPublisher applicationEventPublisher) {
        FileWrapper fileWrapper = new FileWrapperImp();
        ContractSpringEventPublisher contractSpringEventPublisher = new ContractSpringEventPublisher(applicationEventPublisher);
        return new FileFacade(new FileSystemStorage(fileWrapper), contractSpringEventPublisher);
    }
}
