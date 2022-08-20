package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Getter
@EqualsAndHashCode
public class ContractDto {
    private UUID id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private boolean isScanFromTauronUpload;
    private boolean isPreliminaryMapUpload;
    private ContractStepEnum contactStepEnum;
    private LocalDateTime created;
}



