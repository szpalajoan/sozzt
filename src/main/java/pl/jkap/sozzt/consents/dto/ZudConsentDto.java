package pl.jkap.sozzt.consents.dto;

import lombok.*;
import pl.jkap.sozzt.consents.domain.ConsentStatus;
import pl.jkap.sozzt.consents.domain.DeliveryType;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ZudConsentDto {
    private String institutionName;
    private String plotNumber;
    private String comment;
    private Instant consentCreateDate;
    private ConsentStatus consentStatus;
    private Instant consentGivenDate;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;
    private String statusComment;
} 