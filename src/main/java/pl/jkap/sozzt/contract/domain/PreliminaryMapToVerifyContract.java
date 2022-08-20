package pl.jkap.sozzt.contract.domain;

import lombok.Setter;
import pl.jkap.sozzt.contract.exception.MyNotYetImplementedException;

@Setter
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
        throw new MyNotYetImplementedException();
    }
}
