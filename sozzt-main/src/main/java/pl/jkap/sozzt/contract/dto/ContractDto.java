package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
@EqualsAndHashCode
public class ContractDto {
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private boolean isScanFromTauronUpload;
    private ContactStepDto contactStep;
    private LocalDateTime created;

    public enum ContactStepDto {
        DATA_INPUT_STEP,
        WAITING_TO_PRELIMINARY_MAP_STEP
    }
}



