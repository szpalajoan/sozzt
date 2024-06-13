package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.globalvalueobjects.AuditInfo;

import java.io.Serializable;
import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Entity
@Builder
@Getter
class Contract implements Serializable {

    @Id
    private UUID contractId;
    @Embedded
    private ContractDetails contractDetails;
    @Embedded
    private Location location;
    @Embedded
    private AuditInfo auditInfo;
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

    ContractDto dto() {
        return ContractDto.builder()
                .contractId(contractId)
                .contractDetails(contractDetails.dto())
                .location(location.dto())
                .createdBy(auditInfo.getCreatedBy())
                .createdAt(auditInfo.getCreatedAt())
                .isScanFromTauronUploaded(isScanFromTauronUploaded)
                .build();
    }


    void createSteps(ContractStepCreator contractStepCreator) {
        contractSteps.add(contractStepCreator.createPreliminaryPlanStep(contractId, contractDetails.getOrderDate()));
        contractSteps.add(contractStepCreator.createTerrainVisionStep(contractId, contractDetails.getOrderDate()));
    }
}


