package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.exception.ContractStepNotFoundException;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Builder
@Getter
class Contract {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;

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

    ContractDto.ContactStepDto checkCurrentStepDto() {
        if (this.getContractStep() instanceof DataInputStep) {
            return ContractDto.ContactStepDto.DATA_INPUT_STEP;
        }
        if (this.getContractStep() instanceof WaitingToPreliminaryMapStep) {
            return ContractDto.ContactStepDto.WAITING_TO_PRELIMINARY_MAP_STEP;
        }
        throw new ContractStepNotFoundException("Contract step not found");
    }

    ContractDto dto() {
        return ContractDto.builder()
                .id(id)
                .invoiceNumber(invoiceNumber)
                .location(location)
                .executive(executive)
                .isScanFromTauronUpload(checkIsScanFromTauronUploaded())
                .contactStep(checkCurrentStepDto())
                .created(created)
                .build();
    }


}


