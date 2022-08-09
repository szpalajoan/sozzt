package pl.jkap.sozzt.contract.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@SuppressWarnings(value = "unused")
@Configuration
public class ContractConfiguration {

    public ContractFacade contractFacade() {
        ContractRepository contractRepository = new InMemoryContractRepository();
        ContractMapper contractMapper = new ContractMapper();
        return new ContractFacade(contractRepository, contractMapper);
    }

    @Bean
    ContractFacade contractFacade(ContractRepository contractRepository) {
        ContractMapper contractMapper = new ContractMapper();
        return new ContractFacade(contractRepository, contractMapper);
    }
}
