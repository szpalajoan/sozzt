package pl.jkap.sozzt.contractsecurity.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractSecurityConfiguration {

    @Bean
    public ContractSecurityFacade contractSecurityFacade() {
        return new ContractSecurityFacade(new InMemoryContractSecurity());

    }

//    public ContractSecurityFacade contractSecurityFacade() {
//        return new ContractSecurityFacade(new InMemoryContractSecurity());
//    }
}
