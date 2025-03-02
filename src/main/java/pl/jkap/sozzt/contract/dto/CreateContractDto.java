package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class CreateContractDto {
    private UUID contractId;
    private String invoiceNumber;
    private LocationDto location;
    private ContractDetailsDto contractDetailsDto;
    private boolean zudConsentRequired;

    public Optional<UUID> getContractId() {
        return Optional.ofNullable(contractId);
    }
}



