package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;

@Builder
@Setter
class DataInputContract implements Contract {

    private final ContractData contractData;
    private boolean isScanFromTauronUpload;

    DataInputContract(ContractData contractData, boolean isScanFromTauronUpload) {
        this.contractData = contractData;
        this.isScanFromTauronUpload = isScanFromTauronUpload;
    }

    @Override
    public PreliminaryMapToUploadContract confirmStep() {
        if (isScanFromTauronUpload) {
            return new PreliminaryMapToUploadContract(this.contractData, false);
        } else {
            throw new NoScanFileOnConfirmingException("There is no uploaded scan file.");
        }
    }

    @Override
    public void updateContractEntity(ContractEntity contractEntity) {
        contractEntity.setContractData(contractData);
        contractEntity.setContractStepEnum(ContractStepEnum.DATA_INPUT_STEP);
        contractEntity.setScanFromTauronUpload(isScanFromTauronUpload);
    }
}