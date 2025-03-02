package pl.jkap.sozzt.consents.dto;

import lombok.*;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddConsentsDto {
    private UUID contractId;
    private Instant deadline;
    private boolean zudConsentRequired;

    public Optional<UUID> getContractId() {
        return Optional.ofNullable(contractId);
    }
}
