package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;

@Setter
@Builder
class PreliminaryMapToVerifyContract implements Contract {

    private final ContractData contractData;

    PreliminaryMapToVerifyContract(ContractData contractData) {
        this.contractData = contractData;
    }

    @Override
    public ListOfConsentsToAddContract confirmStep() {
        return new ListOfConsentsToAddContract(contractData);
    }

    @Override
    public void updateContractEntity(ContractEntity contractEntity) {
        contractEntity.setContractData(contractData);
        contractEntity.setContractStepEnum(ContractStepEnum.PRELIMINARY_MAP_TO_VERIFY);
    }
}
