package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import pl.jkap.sozzt.consents.domain.ConsentsFacade;
import pl.jkap.sozzt.consents.dto.AddConsentsDto;
import pl.jkap.sozzt.documentation.domain.DocumentationFacade;
import pl.jkap.sozzt.documentation.dto.AddDocumentationDto;
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade;
import pl.jkap.sozzt.preliminaryplanning.dto.AddPreliminaryPlanDto;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;
import pl.jkap.sozzt.terrainvision.dto.AddTerrainVisionDto;

import java.time.Duration;
import java.time.Instant;
import java.util.UUID;

@Builder
class ContractStepCreator {

    private final TerrainVisionFacade terrainVisionFacade;
    private final PreliminaryPlanFacade preliminaryPlanFacade;
    private final ConsentsFacade consentsFacade;
    private final DocumentationFacade documentationFacade;


    ContractStep createPreliminaryPlanStep(UUID contractId, Instant contractOrderDate) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(3));
        preliminaryPlanFacade.addPreliminaryPlan(AddPreliminaryPlanDto.builder()
                .preliminaryPlanId(contractId)
                .deadline(deadline)
                .build());
        return new ContractStep(ContractStepType.PRELIMINARY_PLAN, ContractStepStatus.IN_PROGRESS, deadline);
    }

    ContractStep createTerrainVisionStep(UUID contractId, Instant contractOrderDate) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(21));
        terrainVisionFacade.addTerrainVision(AddTerrainVisionDto.builder()
                .terrainVisionId(contractId)
                .deadLine(deadline)
                .build());
        return new ContractStep(ContractStepType.TERRAIN_VISION, ContractStepStatus.ON_HOLD, deadline);
    }

    ContractStep createProjectPurposesMapPreparationStep(Instant contractOrderDate) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(65));
        return new ContractStep(ContractStepType.PROJECT_PURPOSES_MAP_PREPARATION, ContractStepStatus.NOT_ACTIVE, deadline);
    }

    ContractStep createRoutePreparationStep(Instant contractOrderDate) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(70));
        return new ContractStep(ContractStepType.ROUTE_PREPARATION, ContractStepStatus.ON_HOLD, deadline);
    }

    ContractStep createConsentsCollectionStep(UUID contractId, Instant contractOrderDate, boolean zudConsentRequired) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(79));
            consentsFacade.addConsents(new AddConsentsDto(contractId, deadline, zudConsentRequired));
            return new ContractStep(ContractStepType.CONSENTS_COLLECTION, ContractStepStatus.ON_HOLD, deadline);
    }

    ContractStep createPreparationOfDocumentationStep(UUID contractId, Instant contractOrderDate) {
        Instant deadline = contractOrderDate.plus(Duration.ofDays(183));
        documentationFacade.addDocumentation(new AddDocumentationDto(contractId, deadline));
        return new ContractStep(ContractStepType.PREPARATION_OF_DOCUMENTATION, ContractStepStatus.ON_HOLD, deadline);
    }
}
