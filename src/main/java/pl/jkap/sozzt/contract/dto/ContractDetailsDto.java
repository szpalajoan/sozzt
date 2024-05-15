package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContractDetailsDto {
    private String contractNumber;
    private String workNumber;
    private String customerContractNumber;
    private Instant orderDate;
}
