package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;
import pl.jkap.sozzt.contract.exception.ContractStepNotFoundException;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Builder
@Getter
@Setter
class Contract {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;
    private ContractStep contractStep;

    void setContractStep(ContractStep contractStep) {
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

    ContractStepEnum checkCurrentStepDto() {
        if (this.getContractStep() instanceof DataInputStep) {
            return ContractStepEnum.DATA_INPUT_STEP;
        }
        if (this.getContractStep() instanceof WaitingToPreliminaryMapStep) {
            return ContractStepEnum.WAITING_TO_PRELIMINARY_MAP_STEP;
        }
        throw new ContractStepNotFoundException("Contract step not found");
    }

    ContractEntity toContractEntity() {
        return ContractEntity.builder()
                .id(id)
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .scanFromTauronUpload(checkIsScanFromTauronUploaded())
                .contractStepEnum(checkCurrentStepDto())
                .created(created)
                .build();
    }

}


