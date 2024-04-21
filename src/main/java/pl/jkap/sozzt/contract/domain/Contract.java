package pl.jkap.sozzt.contract.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.globalvalueobjects.AuditInfo;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private UUID contractId;
    private ContractDetails contractDetails;
    private Location location;
    private AuditInfo auditInfo;
    private Instant deadLine;
    private boolean isScanFromTauronUploaded;


    private ContractStep contractStep;
    void confirmStep() {
        this.contractStep.confirmStep();
    }

    void confirmScanUploaded() {
        isScanFromTauronUploaded = true;
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

}


