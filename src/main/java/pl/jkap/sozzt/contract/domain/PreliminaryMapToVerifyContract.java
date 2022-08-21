package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;
import pl.jkap.sozzt.contract.exception.MyNotYetImplementedException;

@Setter
@Builder
class PreliminaryMapToVerifyContract implements Contract {

    private final ContractData contractData;

    PreliminaryMapToVerifyContract(ContractData contractData) {
        this.contractData = contractData;
    }

    @Override
    public PreliminaryMapToVerifyContract confirmStep() {
        throw new MyNotYetImplementedException();
    }

    @Override
    public void updateContractEntity(ContractEntity contractEntity) {
        contractEntity.setContractData(contractData);
        contractEntity.setContractStepEnum(ContractStepEnum.PRELIMINARY_MAP_TO_VERIFY);
    }
}
