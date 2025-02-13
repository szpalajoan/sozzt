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
public class AddZudConsentDto {
    private UUID zudConsentId;
    private String institutionName;
    private String plotNumber;
    private String comment;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;

    public Optional<UUID> getZudConsentId() {
        return Optional.ofNullable(zudConsentId);
    }

    public Optional<DeliveryType> getDeliveryType() {
        return Optional.ofNullable(deliveryType);
    }
} 