package pl.jkap.sozzt.contract.dto;

import lombok.*;

import java.time.Instant;
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
    private String location;

    public Optional<UUID> getId() {
        return Optional.of(id);
    }
}



