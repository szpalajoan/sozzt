package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.exception.MyNotYetImplementedException;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.CONSENTS_TO_ACCEPT;

@Setter
@Builder
class ConsentsToAcceptContract implements Contract {

    private final ContractData contractData;

    ConsentsToAcceptContract(ContractData contractData) {
        this.contractData = contractData;
        this.contractData.setContactStepEnum(CONSENTS_TO_ACCEPT);

    }

    @Override
    public ConsentsToAcceptContract confirmStep() {
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
                .contactStepEnum(contractData.getContactStepEnum())
                .build();
    }
}
