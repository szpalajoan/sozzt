package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import org.hibernate.cfg.NotYetImplementedException;
import pl.jkap.sozzt.contract.dto.ContractDataDto;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.MAP_UNDER_THE_LAW_UPLOAD;

@Builder
@Getter
class MapUnderTheLawUploadContract implements Contract {

    private final ContractData contractData;

    MapUnderTheLawUploadContract(ContractData contractData) {
        this.contractData = contractData;
        this.contractData.setContactStepEnum(MAP_UNDER_THE_LAW_UPLOAD);
    }

    @Override
    public MapUnderTheLawUploadContract confirmStep() {
        throw new NotYetImplementedException();
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
