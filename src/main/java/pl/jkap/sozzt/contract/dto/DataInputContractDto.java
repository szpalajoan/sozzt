package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataInputContractDto {
    ContractDataDto contractDataDto;
    private boolean isScanFromTauronUpload;
}
