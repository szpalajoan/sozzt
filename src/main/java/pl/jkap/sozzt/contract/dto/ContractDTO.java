package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Builder
@Getter
@EqualsAndHashCode
public class ContractDTO {
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;

}
