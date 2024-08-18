package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.exception.ContractStepNotFoundException;
import pl.jkap.sozzt.globalvalueobjects.AuditInfo;

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
    }

    void finalizePreliminaryPlan() {
        ContractStep preliminaryPlanStep = contractSteps.stream()
                .filter(step -> step.getContractStepType() == ContractStepType.PRELIMINARY_PLAN)
                .findFirst()
                .orElseThrow(() -> new ContractStepNotFoundException("Preliminary plan step not found"));
        ContractStep terrainVisionStep = contractSteps.stream()
                .filter(step -> step.getContractStepType() == ContractStepType.TERRAIN_VISION)
                .findFirst()
                .orElseThrow(() -> new ContractStepNotFoundException("Terrain vision step not found"));
        preliminaryPlanStep.completeStep();
        terrainVisionStep.beginStep();
    }

    boolean isContractCompleted() {
        return contractDetails != null
                && location != null
                && isScanFromTauronUploaded;
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


