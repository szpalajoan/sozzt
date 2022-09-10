package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.PRELIMINARY_MAP_TO_VERIFY;

@Setter
@Builder
class PreliminaryMapToVerifyContract implements Contract {

    private final ContractData contractData;

    PreliminaryMapToVerifyContract(ContractData contractData) {
        this.contractData = contractData;
        this.contractData.setContractStepEnum(PRELIMINARY_MAP_TO_VERIFY);
    }

    @Override
    public ListOfConsentsToAddContract confirmStep() {
        return new ListOfConsentsToAddContract(contractData);
    }

    @Override
    public ContractDataDto dto() {
        return ContractDataDto.builder()
                .id(contractData.getId())
                .invoiceNumber(contractData.getInvoiceNumber())
                .location(contractData.getLocation())
                .executive(contractData.getExecutive())
                .created(contractData.getCreated())
                .contactStepEnum(contractData.getContractStepEnum())
                .build();
    }

    @Override
    public ContractData getContractData() {
        return contractData;
    }
}
