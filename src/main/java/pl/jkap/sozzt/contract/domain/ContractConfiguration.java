package pl.jkap.sozzt.contract.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;

@Configuration
public class ContractConfiguration {



    @Bean
    ContractFacade contractFacade(ContractSecurityFacade contractSecurityFacade,
                                  PreliminaryPlanFacade preliminaryPlanFacade,
                                  TerrainVisionFacade terrainVisionFacade,
                                  ContractRepository contractRepository,
                                  InstantProvider instantProvider) {
        ContractCreator contractCreator = new ContractCreator(instantProvider);
        return ContractFacade.builder()
                .contractRepository(new InMemoryContractRepository())
                .contractSecurityFacade(contractSecurityFacade)
                .preliminaryPlanFacade(preliminaryPlanFacade)
                .contractCreator(contractCreator)
                .contractStepCreator(contractStepCreator(preliminaryPlanFacade, terrainVisionFacade))
                .instantProvider(instantProvider)
                .build();
    }

    public ContractFacade contractFacade(ContractSecurityFacade contractSecurityFacade,
                                         PreliminaryPlanFacade preliminaryPlanFacade,
                                         TerrainVisionFacade terrainVisionFacade,
                                         InstantProvider instantProvider){
        return contractFacade(contractSecurityFacade, preliminaryPlanFacade, terrainVisionFacade, new InMemoryContractRepository(), instantProvider);
    }

    private ContractStepCreator contractStepCreator(PreliminaryPlanFacade preliminaryPlanFacade, TerrainVisionFacade terrainVisionFacade) {
        return ContractStepCreator.builder()
                .preliminaryPlanFacade(preliminaryPlanFacade)
                .terrainVisionFacade(terrainVisionFacade)
                .build();
    }
}
