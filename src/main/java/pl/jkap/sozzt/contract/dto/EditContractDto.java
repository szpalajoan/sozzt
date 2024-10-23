package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

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



