package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.dto.DataInputContractDto;
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;
import pl.jkap.sozzt.contract.exception.WithdrawalException;

@Builder
@Setter
@Getter
class DataInputContract implements Contract {

    private ContractData contractData;
    private boolean isScanFromTauronUpload;

    DataInputContract(ContractData contractData, boolean isScanFromTauronUpload) {
        this.contractData = contractData;
        this.isScanFromTauronUpload = isScanFromTauronUpload;
    }

    @Override
    public PreliminaryMapToUploadContract confirmStep() {
        if (isScanFromTauronUpload) {
            return new PreliminaryMapToUploadContract(this.contractData, false);
        } else {
            throw new NoScanFileOnConfirmingException("There is no uploaded scan file.");
        }
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

    @Override
    public PreliminaryMapToUploadContract withdrawalToNewPreliminaryMapUpload() {
        throw new WithdrawalException("This step can't be withdrawn");
    }

    DataInputContractDto dataInputContractDto() {
        return DataInputContractDto.builder()
                .contractDataDto(dto())
                .isScanFromTauronUpload(isScanFromTauronUpload)
                .build();
    }
}