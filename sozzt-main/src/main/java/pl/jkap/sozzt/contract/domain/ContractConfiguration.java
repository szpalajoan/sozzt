package pl.jkap.sozzt.contract.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.instant.InstantProvider;

@Configuration
public class ContractConfiguration {
    @Bean
    ContractFacade contractFacade(ContractRepository contractRepository, InstantProvider instantProvider) {
        ContractCreator contractCreator = new ContractCreator();
        return ContractFacade.builder()
                .contractRepository(contractRepository)
                .contractCreator(contractCreator)
                .instantProvider(instantProvider)
                .build();
    }

    public ContractFacade contractFacade(InstantProvider instantProvider){
        return contractFacade(new InMemoryContractRepository(), instantProvider);
    }
}
