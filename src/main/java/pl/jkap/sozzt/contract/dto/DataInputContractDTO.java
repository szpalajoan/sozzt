package pl.jkap.sozzt.contract.dto;

import lombok.Builder;
import lombok.Getter;

@Builder
@Getter
public class DataInputContractDTO {
    private Long id;
    private String invoiceNumber;
    private String location;
    private String executive;


}
