package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
class ContractData {

    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;
    private Contract contract;

}


