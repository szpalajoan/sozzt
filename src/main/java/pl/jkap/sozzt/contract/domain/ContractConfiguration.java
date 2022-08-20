package pl.jkap.sozzt.contract.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractConfiguration {

    public ContractFacade contractFacade() {
        ContractRepository contractRepository = new InMemoryContractRepository();
        ContractMapper contractMapper = new ContractMapper();
        ContractCreator contractCreator = new ContractCreator();
        return new ContractFacade(contractRepository, contractMapper, contractCreator);
    }

    @Bean
    ContractFacade contractFacade(ContractRepository contractRepository) {
        ContractMapper contractMapper = new ContractMapper();
        ContractCreator contractCreator = new ContractCreator();
        return new ContractFacade(contractRepository, contractMapper, contractCreator);
    }
}
