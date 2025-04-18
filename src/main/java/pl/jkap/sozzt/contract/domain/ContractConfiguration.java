package pl.jkap.sozzt.contract.domain;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import pl.jkap.sozzt.consents.domain.ConsentsFacade;
import pl.jkap.sozzt.contractsecurity.domain.ContractSecurityFacade;
import pl.jkap.sozzt.documentation.domain.DocumentationFacade;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.landextracts.domain.LandExtractsFacade;
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade;
import pl.jkap.sozzt.remark.domain.RemarkFacade;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade;
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationFacade;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;

@Configuration
public class ContractConfiguration {

    @Bean
    public ContractFacade contractFacade(ContractSecurityFacade contractSecurityFacade,
                                  PreliminaryPlanFacade preliminaryPlanFacade,
                                  TerrainVisionFacade terrainVisionFacade,
                                  ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade,
                                  RoutePreparationFacade routePreparationFacade,
                                  LandExtractsFacade landExtractsFacade,
                                  ConsentsFacade consentsFacade,
                                  DocumentationFacade documentationFacade,
                                  RemarkFacade remarkFacade,
                                  InstantProvider instantProvider) {
        ContractCreator contractCreator = new ContractCreator(instantProvider);
        return ContractFacade.builder()
                .contractRepository(new InMemoryContractRepository())
                .contractSecurityFacade(contractSecurityFacade)
                .terrainVisionFacade(terrainVisionFacade)
                .projectPurposesMapPreparationFacade(projectPurposesMapPreparationFacade)
                .routePreparationFacade(routePreparationFacade)
                .landExtractsFacade(landExtractsFacade)
                .contractCreator(contractCreator)
                .contractStepCreator(contractStepCreator(preliminaryPlanFacade, terrainVisionFacade, consentsFacade, documentationFacade))
                .remarkFacade(remarkFacade)
                .build();
    }

//    public ContractFacade contractFacade(ContractSecurityFacade contractSecurityFacade,
//                                         PreliminaryPlanFacade preliminaryPlanFacade,
//                                         TerrainVisionFacade terrainVisionFacade,
//                                         InstantProvider instantProvider){
//        return contractFacade(contractSecurityFacade, preliminaryPlanFacade, terrainVisionFacade, new InMemoryContractRepository(), instantProvider);
//    }

    private ContractStepCreator contractStepCreator(PreliminaryPlanFacade preliminaryPlanFacade,
                                                    TerrainVisionFacade terrainVisionFacade,
                                                    ConsentsFacade consentsFacade,
                                                    DocumentationFacade documentationFacade) {
        return ContractStepCreator.builder()
                .preliminaryPlanFacade(preliminaryPlanFacade)
                .terrainVisionFacade(terrainVisionFacade)
                .consentsFacade(consentsFacade)
                .documentationFacade(documentationFacade)
                .build();
    }
}
