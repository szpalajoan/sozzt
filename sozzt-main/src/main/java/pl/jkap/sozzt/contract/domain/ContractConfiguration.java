package pl.jkap.sozzt.contract.domain;

import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractConfiguration {
    public ContractFacade contractFacade() {
        ContractRepository contractRepository = new InMemoryContractRepository();
        ContractCreator contractCreator = new ContractCreator();
        return new ContractFacade(contractRepository, contractCreator);
    }

}
