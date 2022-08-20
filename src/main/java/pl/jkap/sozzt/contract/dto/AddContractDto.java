package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;


@Builder
@Getter
@EqualsAndHashCode
public class AddContractDto {
    private String invoiceNumber;
    private String location;
    private String executive;
}



