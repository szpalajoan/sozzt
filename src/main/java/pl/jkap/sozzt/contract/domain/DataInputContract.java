package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;

@Builder
@Setter
class DataInputContract implements Contract {

    private ContractData contractData;
    private boolean isScanFromTauronUpload;

    DataInputContract(ContractData contractData, boolean isScanFromTauronUpload) {
        this.contractData = contractData;
        this.isScanFromTauronUpload = isScanFromTauronUpload;
    }

    @Override
    public WaitingToPreliminaryMapContract confirmStep() {
        if (isScanFromTauronUpload) {
            return new WaitingToPreliminaryMapContract(this.contractData);
        } else {
            throw new NoScanFileOnConfirmingException("There is no uploaded scan file.");
        }
    }

    public ContractEntity toContractEntity() {
        return ContractEntity.builder()
                .id(contractData.getId())
                .invoiceNumber(contractData.getInvoiceNumber())
                .location(contractData.getLocation())
                .executive(contractData.getExecutive())
                .scanFromTauronUpload(isScanFromTauronUpload)
                .contractStepEnum(ContractStepEnum.DATA_INPUT_STEP)
                .created(contractData.getCreated())
                .build();
    }
}


