package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import pl.jkap.sozzt.contract.dto.ContractDataDto;
import pl.jkap.sozzt.contract.dto.DataInputContractDto;
import pl.jkap.sozzt.contract.exception.NoScanFileOnConfirmingException;

@Builder
@Setter
@Getter
class DataInputContract implements Contract {

    private ContractData contractData;
    private boolean scanFromTauronUpload;

    DataInputContract(ContractData contractData, boolean scanFromTauronUpload) {
        this.contractData = contractData;
        this.scanFromTauronUpload = scanFromTauronUpload;
    }

    @Override
    public PreliminaryMapToUploadContract confirmStep() {
        if (scanFromTauronUpload) {
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
                .contactStepEnum(contractData.getContractStepEnum())
                .build();
    }

    DataInputContractDto dataInputContractDto() {
        return DataInputContractDto.builder()
                .contractDataDto(dto())
                .isScanFromTauronUpload(scanFromTauronUpload)
                .build();
    }

}