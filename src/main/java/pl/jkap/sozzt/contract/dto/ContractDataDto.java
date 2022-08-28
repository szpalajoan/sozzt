package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.UUID;


@Builder
@Getter
public class ContractDataDto {
    private UUID id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private ContractStepEnum contactStepEnum;
    private LocalDateTime created;
}



