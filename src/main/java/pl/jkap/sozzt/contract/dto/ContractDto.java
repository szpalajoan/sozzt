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
    private boolean scanFromTauronUpload;
    private ContractStepEnum contactStepEnum;
    private LocalDateTime created;
}



