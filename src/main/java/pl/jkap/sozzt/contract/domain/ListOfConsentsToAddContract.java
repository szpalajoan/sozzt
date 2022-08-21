package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractStepEnum;
import pl.jkap.sozzt.contract.exception.MyNotYetImplementedException;

@Setter
@Builder
class ListOfConsentsToAddContract implements Contract {

    private final ContractData contractData;

    ListOfConsentsToAddContract(ContractData contractData) {
        this.contractData = contractData;
    }

    @Override
    public ListOfConsentsToAddContract confirmStep() {
        throw new MyNotYetImplementedException();
    }

    @Override
    public void updateContractEntity(ContractEntity contractEntity) {
        contractEntity.setContractData(contractData);
        contractEntity.setContractStepEnum(ContractStepEnum.LIST_OF_CONSENTS_TO_ADD);
    }
}
