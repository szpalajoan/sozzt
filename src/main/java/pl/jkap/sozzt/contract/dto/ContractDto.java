package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Getter
public class ContractDto {
    private UUID id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private boolean isScanFromTauronUpload;
    private boolean isPreliminaryMapUpload;
    private boolean allConsentAccepted;
    private ContractStepEnum contactStepEnum;
    private LocalDateTime created;
}



