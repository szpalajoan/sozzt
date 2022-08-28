package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class PreliminaryMapToUploadContractDto {
    ContractDataDto contractDataDto;
    private boolean isPreliminaryMapUpload;
}
