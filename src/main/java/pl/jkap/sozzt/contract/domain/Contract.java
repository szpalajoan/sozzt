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
    private UUID id;
    private ContractDetails contractDetails;
    private Location location;
    private AuditInfo auditInfo;
    private Instant deadLine;


    private ContractStep contractStep;

    void changeStep(ContractStep contractStep) {
        this.contractStep = contractStep;
    }

    void confirmStep() {
        this.contractStep.confirmStep();
    }

    void confirmScanUploaded() {
        if (this.getContractStep() instanceof DataInputStep) {
            ((DataInputStep) this.getContractStep()).setScanFromTauronUpload();
        }
    }

    boolean checkIsScanFromTauronUploaded() {
        if (this.getContractStep() instanceof DataInputStep) {
            return ((DataInputStep) this.getContractStep()).getIsScanFromTauronUpload();
        }
        return true;
    }

    ContractDto dto() {
        return ContractDto.builder()
                .id(id)
                .contractDetails(contractDetails.dto())
                .location(location.dto())
                .createdBy(auditInfo.getCreatedBy())
                .createdAt(auditInfo.getCreatedAt())
                .build();
    }


}


