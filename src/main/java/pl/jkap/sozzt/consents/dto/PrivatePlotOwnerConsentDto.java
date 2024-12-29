package pl.jkap.sozzt.consents.dto;

import lombok.*;
import pl.jkap.sozzt.consents.domain.ConsentStatus;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class PrivatePlotOwnerConsentDto {
    private UUID privatePlotOwnerConsentId;
    private String ownerName;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
    private String plotNumber;
    private Instant consentCreateDate;
    private Instant consentGivenDate;
    private ConsentStatus consentStatus;
    private String collectorName;
}
