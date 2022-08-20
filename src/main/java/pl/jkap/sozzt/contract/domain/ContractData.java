package pl.jkap.sozzt.contract.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Builder
@Getter
@Setter
class ContractData {

    private UUID id;
    private String invoiceNumber;
    private String location;
    private String executive;
    private LocalDateTime created;
    private Contract contract;

}


