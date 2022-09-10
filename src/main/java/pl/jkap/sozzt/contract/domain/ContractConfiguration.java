package pl.jkap.sozzt.contract.domain;

import org.mapstruct.factory.Mappers;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractConfiguration {

    public ContractFacade contractFacade() {
        ContractRepository contractRepository = new InMemoryContractRepository();
        return contractFacade(contractRepository);
    }

    @Bean
    ContractFacade contractFacade(ContractRepository contractRepository) {
        ContractCreator contractCreator = new ContractCreator();
        ContractMapperInterface mapper = Mappers.getMapper(ContractMapperInterface.class);
        return new ContractFacade(contractRepository, contractCreator, mapper);
    }
}
