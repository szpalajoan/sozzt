package pl.jkap.sozzt.contract.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;

@Configuration
public class ContractConfiguration {

    @Bean
    public ContractStepCreator contractStepCreator(PreliminaryPlanFacade preliminaryPlanFacade, TerrainVisionFacade terrainVisionFacade) {
        return ContractStepCreator.builder()
                .preliminaryPlanFacade(preliminaryPlanFacade)
                .terrainVisionFacade(terrainVisionFacade)
                .build();
    }
    @Bean
    ContractFacade contractFacade(ContractStepCreator contractStepCreator, ContractRepository contractRepository, InstantProvider instantProvider) {
        ContractCreator contractCreator = new ContractCreator(instantProvider);
        return ContractFacade.builder()
                .contractRepository(contractRepository)
                .contractCreator(contractCreator)
                .contractStepCreator(contractStepCreator)
                .instantProvider(instantProvider)
                .build();
    }

    public ContractFacade contractFacade(ContractStepCreator contractStepCreator, InstantProvider instantProvider){
        return contractFacade(contractStepCreator, new InMemoryContractRepository(), instantProvider);
    }
}
