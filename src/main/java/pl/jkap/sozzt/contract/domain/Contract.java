package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Embedded;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.EditContractDto;
import pl.jkap.sozzt.contract.exception.ContractStepNotFoundException;
import pl.jkap.sozzt.globalvalueobjects.AuditInfo;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.AddProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Entity
@Builder
@Getter
@ToString
class Contract implements Serializable {

    @Id
    private UUID contractId;
    @Embedded
    private ContractDetails contractDetails;
    @Embedded
    private Location location;
    @Embedded
    private AuditInfo auditInfo; //TODO dopisz uaktualnienie auditu
    private Instant deadLine;
    @Embedded
    private ContractProgress contractProgress;
    private boolean isScanFromTauronUploaded;

    @OneToMany
    private Collection<ContractStep> contractSteps;


    void confirmScanUploaded() {
        isScanFromTauronUploaded = true;
    }

    void scanDeleted() {
        isScanFromTauronUploaded = false;
    }

    void createSteps(ContractStepCreator contractStepCreator) {
        contractSteps.add(contractStepCreator.createPreliminaryPlanStep(contractId, contractDetails.getOrderDate()));
        contractSteps.add(contractStepCreator.createTerrainVisionStep(contractId, contractDetails.getOrderDate()));
        contractSteps.add(contractStepCreator.createProjectPurposesMapPreparationStep(contractDetails.getOrderDate()));
        contractSteps.add(contractStepCreator.createConsentsCollectionStep(contractId, contractDetails.getOrderDate()));
        contractSteps.add(contractStepCreator.createPreparationOfDocumentationStep(contractId, contractDetails.getOrderDate()));
    }

    void completePreliminaryPlan(TerrainVisionFacade terrainVisionFacade) {
        completePreliminaryPlanStep();
        beginTerrainVisionStep(terrainVisionFacade);
    }

    private void completePreliminaryPlanStep() {
        ContractStep preliminaryPlanStep = getContractStep(ContractStepType.PRELIMINARY_PLAN);
        preliminaryPlanStep.completeStep();
    }
    private void beginTerrainVisionStep(TerrainVisionFacade terrainVisionFacade) {
        terrainVisionFacade.beginTerrainVision(contractId);
        ContractStep terrainVisionStep = getContractStep(ContractStepType.TERRAIN_VISION);
        terrainVisionStep.beginStep();
    }

    void completeTerrainVision(ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade, boolean projectPurposesMapPreparationNeed) {
        completeTerrainVisionStep();
        if(projectPurposesMapPreparationNeed){
            beginProjectPurposesMapPreparationStep(projectPurposesMapPreparationFacade);
        }
        beginConsentsCollectionStep();
    }

    void completeProjectPurposesMapPreparation() {
        completeProjectPurposesMapPreparationStep();
        beginPreparationDocumentationStep();
    }

    void completeConsentsCollection() {
        completeConsentsCollectionStep();
        beginPreparationDocumentationStep();
    }

    void completePreparationDocumentation() {
        completePreparationDocumentationStep();
    }

    private void completeProjectPurposesMapPreparationStep() {
        ContractStep projectPurposesMapPreparationStep = getContractStep(ContractStepType.PROJECT_PURPOSES_MAP_PREPARATION);
        projectPurposesMapPreparationStep.completeStep();
    }

    private void completeTerrainVisionStep() {
        ContractStep terrainVisionStep = getContractStep(ContractStepType.TERRAIN_VISION);
        terrainVisionStep.completeStep();
    }

    private void beginProjectPurposesMapPreparationStep(ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade) {
        ContractStep projectPurposesMapPreparationStep = getContractStep(ContractStepType.PROJECT_PURPOSES_MAP_PREPARATION);
        projectPurposesMapPreparationFacade.addProjectPurposesMapPreparation(AddProjectPurposesMapPreparationDto.builder()
                .projectPurposesMapPreparationId(contractId)
                .deadline(projectPurposesMapPreparationStep.getDeadline())
                .build());
        projectPurposesMapPreparationStep.beginStep();
    }

    private void beginConsentsCollectionStep() {
        ContractStep consentsCollectionStep = getContractStep(ContractStepType.CONSENTS_COLLECTION);
        consentsCollectionStep.beginStep();
    }

    private void completeConsentsCollectionStep() {
        ContractStep consentsCollectionStep = getContractStep(ContractStepType.CONSENTS_COLLECTION);
        consentsCollectionStep.completeStep();
    }

    private void beginPreparationDocumentationStep() {
        ContractStep consentsCollectionStep = getContractStep(ContractStepType.CONSENTS_COLLECTION);
        ContractStep projectPurposesMapPreparationStep = getContractStep(ContractStepType.PROJECT_PURPOSES_MAP_PREPARATION);
        if(consentsCollectionStep.isCompleted() && projectPurposesMapPreparationStep.isCompleted()) {
            ContractStep preparationDocumentationStep = getContractStep(ContractStepType.PREPARATION_OF_DOCUMENTATION);
            preparationDocumentationStep.beginStep();
        }
    }

    private void completePreparationDocumentationStep() {
        ContractStep preparationDocumentationStep = getContractStep(ContractStepType.PREPARATION_OF_DOCUMENTATION);
        preparationDocumentationStep.completeStep();
    }

    private ContractStep getContractStep(ContractStepType contractStepType) {
        return contractSteps.stream()
                .filter(step -> step.getContractStepType() == contractStepType)
                .findFirst()
                .orElseThrow(() -> new ContractStepNotFoundException("Contract step:" + contractStepType + "not found"));
    }

    boolean isContractCompleted() {
        return contractDetails != null
                && location != null
                && isScanFromTauronUploaded;
    }

    void edit(EditContractDto editContractDto) {
        contractDetails = new ContractDetails(editContractDto.getContractDetails());
        location = new Location(editContractDto.getLocation());
    }

    ContractDto dto() {
        return ContractDto.builder()
                .contractId(contractId)
                .contractDetails(contractDetails.dto())
                .location(location.dto())
                .createdBy(auditInfo.getCreatedBy())
                .createdAt(auditInfo.getCreatedAt())
                .isScanFromTauronUploaded(isScanFromTauronUploaded)
                .contractSteps(contractSteps.stream().map(ContractStep::dto).toList())
                .build();
    }

}


