package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.exception.NoPreliminaryMapOnConfirmingException;

import static pl.jkap.sozzt.contract.dto.ContractStepEnum.PRELIMINARY_MAP_TO_UPLOAD;

@Setter
@Builder
@Getter
class PreliminaryMapToUploadContract implements Contract {

    private final ContractData contractData;
    private boolean isPreliminaryMapUpload;

    PreliminaryMapToUploadContract(ContractData contractData, boolean isPreliminaryMapUpload) {
        this.contractData = contractData;
        this.contractData.setContactStepEnum(PRELIMINARY_MAP_TO_UPLOAD);
        this.isPreliminaryMapUpload = isPreliminaryMapUpload;
    }

    @Override
    public PreliminaryMapToVerifyContract confirmStep() {
        if (isPreliminaryMapUpload) {
            return new PreliminaryMapToVerifyContract(this.contractData);
        } else {
            throw new NoPreliminaryMapOnConfirmingException("There is no uploaded preliminary map file.");
        }
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
