package pl.jkap.sozzt.fileContract.domain;


import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.config.application.ContractSpringEventPublisher;

@Configuration
public class FileContractConfiguration {

    @Bean
    FileContractFacade fileContractFacade(ApplicationEventPublisher applicationEventPublisher) {
        FileWrapper fileWrapper = new FileWrapperImp();
        ContractSpringEventPublisher contractSpringEventPublisher = new ContractSpringEventPublisher(applicationEventPublisher);
        return new FileContractFacade(new FileSystemStorage(contractSpringEventPublisher, fileWrapper));
    }

}
