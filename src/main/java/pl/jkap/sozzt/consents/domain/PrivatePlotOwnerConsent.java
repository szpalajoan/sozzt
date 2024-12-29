package pl.jkap.sozzt.consents.domain;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto;
import pl.jkap.sozzt.globalvalueobjects.Address;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@ToString
class PrivatePlotOwnerConsent {

    private UUID privatePlotOwnerConsentId;
    private String ownerName;
    private Address ownerAddress;
    private String plotNumber;
    private Instant consentCreateDate;
    private Instant consentGivenDate;
    private ConsentStatus consentStatus;
    private String collectorName;

    PrivatePlotOwnerConsentDto dto() {
        return PrivatePlotOwnerConsentDto.builder()
                .privatePlotOwnerConsentId(privatePlotOwnerConsentId)
                .ownerName(ownerName)
                .street(ownerAddress.getStreet())
                .houseNumber(ownerAddress.getHouseNumber())
                .apartmentNumber(ownerAddress.getApartmentNumber())
                .postalCode(ownerAddress.getPostalCode())
                .city(ownerAddress.getCity())
                .plotNumber(plotNumber)
                .consentCreateDate(consentCreateDate)
                .consentGivenDate(consentGivenDate)
                .consentStatus(consentStatus)
                .collectorName(collectorName)
                .build();
    }

    void accept(Instant now) {
        this.consentGivenDate = now;
        this.consentStatus = ConsentStatus.CONSENT_GIVEN;
    }
}
