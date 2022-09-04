package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.LIST_OF_CONSENTS_TO_ADD;

@Setter
@Builder
class ListOfConsentsToAddContract implements Contract {

    private final ContractData contractData;

    ListOfConsentsToAddContract(ContractData contractData) {
        this.contractData = contractData;
        this.contractData.setContactStepEnum(LIST_OF_CONSENTS_TO_ADD);
    }

    @Override
    public ConsentsToAcceptContract confirmStep() {
        return new ConsentsToAcceptContract(contractData, false);
    }

    @Override
    public PreliminaryMapToUploadContract withdrawalToNewPreliminaryMapUpload() {
        return new PreliminaryMapToUploadContract(contractData, false);
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
