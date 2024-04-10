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
    private UUID id;
    private String invoiceNumber;
    private LocationDto location;
    private ContractDetailsDto contractDetailsDto;

    public Optional<UUID> getId() {
        return Optional.of(id);
    }
}



