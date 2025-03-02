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
public class ContractDto {
    private UUID contractId;
    private ContractDetailsDto contractDetails;
    private LocationDto location;
    private String createdBy;
    private Instant createdAt;
    private boolean isScanFromTauronUploaded;
    private Collection<ContractStepDto> contractSteps;
    private boolean zudConsentRequired;
}



