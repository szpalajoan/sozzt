package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;
import pl.jkap.sozzt.contract.exception.NoPreliminaryMapOnConfirmingException;

@Setter
@Builder
class PreliminaryMapToUploadContract implements Contract {

    private final ContractData contractData;
    private boolean isPreliminaryMapUpload;

    PreliminaryMapToUploadContract(ContractData contractData, boolean isPreliminaryMapUpload) {
        this.contractData = contractData;
        this.isPreliminaryMapUpload = isPreliminaryMapUpload;
    }

    @Override
    public PreliminaryMapToVerifyContract confirmStep() {
        if (isPreliminaryMapUpload) {
            return new PreliminaryMapToVerifyContract(this.contractData);
        } else {
            throw new NoPreliminaryMapOnConfirmingException("There is no uploaded preliminary map file.");
        }
    }

    @Override
    public void updateContractEntity(ContractEntity contractEntity) {
        contractEntity.setContractData(contractData);
        contractEntity.setContractStepEnum(ContractStepEnum.WAITING_TO_PRELIMINARY_MAP_STEP);
        contractEntity.setPreliminaryMapUpload(isPreliminaryMapUpload);
    }
}
