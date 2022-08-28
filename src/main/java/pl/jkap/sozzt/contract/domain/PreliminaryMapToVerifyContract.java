package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.DATA_INPUT_STEP;

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
    public ContractDataDto dto() {
        return ContractDataDto.builder()
                .id(contractData.getId())
                .invoiceNumber(contractData.getInvoiceNumber())
                .location(contractData.getLocation())
                .executive(contractData.getExecutive())
                .created(contractData.getCreated())
                .contactStepEnum(DATA_INPUT_STEP)
                .build();
    }

    @Override
    public ContractData getContractData() {
        return contractData;
    }
}
