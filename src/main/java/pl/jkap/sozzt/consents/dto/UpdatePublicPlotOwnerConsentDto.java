package pl.jkap.sozzt.consents.dto;

import lombok.*;
import pl.jkap.sozzt.consents.domain.DeliveryType;

import java.time.Instant;
import java.util.Optional;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdatePublicPlotOwnerConsentDto {
    private String publicOwnerName;
    private String comment;
    private String plotNumber;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;

    public Optional<DeliveryType> getDeliveryType() {
        return Optional.ofNullable(deliveryType);
    }
}
