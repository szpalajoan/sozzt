package pl.jkap.sozzt.consents.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddPublicPlotOwnerConsentDto {
    private UUID publicPlotOwnerConsentId;
    private String ownerName;
    private String street;
    private String houseNumber;
    private String apartmentNumber;
    private String postalCode;
    private String city;
    private String plotNumber;
    private String collectorName;
}
