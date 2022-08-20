package pl.jkap.sozzt.contract.domain;

import pl.jkap.sozzt.contract.dto.ContractStepEnum;
import pl.jkap.sozzt.contract.exception.MyNotYetImplementedException;

class PreliminaryMapContract implements Contract {

    private final ContractData contractData;

    PreliminaryMapContract(ContractData contractData) {
        this.contractData = contractData;
    }

    @Override
    public PreliminaryMapContract confirmStep() {
        throw new MyNotYetImplementedException();
    }

    @Override
    public ContractEntity toContractEntity() {
        return ContractEntity.builder()
                .id(contractData.getId())
                .invoiceNumber(contractData.getInvoiceNumber())
                .location(contractData.getLocation())
                .executive(contractData.getExecutive())
                .contractStepEnum(ContractStepEnum.WAITING_TO_PRELIMINARY_MAP_STEP)
                .created(contractData.getCreated())
                .build();
    }
}
