package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ContractDto {
    private UUID id;
    private ContractDetailsDto contractDetails;
    private LocationDto location;
    private String createdBy;
    private Instant createdAt;
}



