package pl.jkap.sozzt.contract.controller.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class ContractDTO {
    private Long id_contract_basic_data;
    private String invoice_number;
    private String location;
    private String executive;
    private LocalDateTime created;
}
