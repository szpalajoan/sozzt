package pl.jkap.sozzt.consents.dto;

import lombok.*;
import pl.jkap.sozzt.consents.domain.DeliveryType;

import java.time.Instant;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class UpdateZudConsentDto {
    private String institutionName;
    private String plotNumber;
    private String comment;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;
} 