package pl.jkap.sozzt.contract.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContractDetailsDto {
    private String contractNumber;
    private String workNumber;
    private String customerContractNumber;
}
