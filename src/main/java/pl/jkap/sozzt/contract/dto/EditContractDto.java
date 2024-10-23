package pl.jkap.sozzt.contract.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class EditContractDto {
    private ContractDetailsDto contractDetails;
    private LocationDto location;
}

