package pl.jkap.sozzt.consents.dto;

import lombok.*;
import pl.jkap.sozzt.consents.domain.DeliveryType;

import java.time.Instant;
import java.util.Optional;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddPrivatePlotOwnerConsentDto {
    private UUID privatePlotOwnerConsentId;
    private String ownerName;
    private String comment;
    private String plotNumber;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;

    public Optional<UUID> getPrivatePlotOwnerConsentId() {
        return Optional.ofNullable(privatePlotOwnerConsentId);
    }

    public Optional<DeliveryType> getDeliveryType() {
        return Optional.ofNullable(deliveryType);
    }

}
