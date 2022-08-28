package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.exception.MyNotYetImplementedException;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.DATA_INPUT_STEP;

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
    public ContractData getContractData() {
        return contractData;
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
}
