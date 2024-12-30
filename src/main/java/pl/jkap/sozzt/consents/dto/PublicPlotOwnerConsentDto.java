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
public class PublicPlotOwnerConsentDto {
    private UUID publicPlotOwnerConsentId;
    private String publicOwnerName;
    private String comment;
    private String plotNumber;
    private Instant consentCreateDate;
    private Instant consentGivenDate;
    private ConsentStatus consentStatus;
    private String statusComment;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;

}
