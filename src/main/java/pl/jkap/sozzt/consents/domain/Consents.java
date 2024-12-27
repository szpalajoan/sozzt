package pl.jkap.sozzt.consents.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.consents.dto.AddPrivatePlotOwnerConsentDto;
import pl.jkap.sozzt.consents.dto.AddPublicPlotOwnerConsentDto;
import pl.jkap.sozzt.consents.dto.ConsentsDto;
import pl.jkap.sozzt.globalvalueobjects.Address;
import pl.jkap.sozzt.instant.InstantProvider;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
class Consents {

    private UUID consentId;
    private Instant deadline;
    private boolean requestForPlotExtractsSent;
    private Collection<PrivatePlotOwnerConsent> privatePlotOwnerConsents;
    private Collection<PublicOwnerConsent> publicOwnerConsents;

    void requestForLandExtractsSent() {
        requestForPlotExtractsSent = true;
    }

    PrivatePlotOwnerConsent addPrivatePlotOwnerConsent(AddPrivatePlotOwnerConsentDto addPrivatePlotOwnerConsentDto, InstantProvider instantProvider) {
        PrivatePlotOwnerConsent consent = PrivatePlotOwnerConsent.builder()
                .privatePlotOwnerConsentId(addPrivatePlotOwnerConsentDto.getPrivatePlotOwnerConsentId())
                .ownerName(addPrivatePlotOwnerConsentDto.getOwnerName())
                .plotNumber(addPrivatePlotOwnerConsentDto.getPlotNumber())
                .ownerAddress(Address.of(addPrivatePlotOwnerConsentDto.getStreet(),
                        addPrivatePlotOwnerConsentDto.getHouseNumber(),
                        addPrivatePlotOwnerConsentDto.getApartmentNumber(),
                        addPrivatePlotOwnerConsentDto.getPostalCode(),
                        addPrivatePlotOwnerConsentDto.getCity()))
                .consentCreateDate(instantProvider.now())
                .consentStatus(ConsentStatus.CONSENT_CREATED)
                .collectorName(addPrivatePlotOwnerConsentDto.getCollectorName())
                .build();
        privatePlotOwnerConsents.add(consent);
        return consent;
    }

    PublicOwnerConsent addPublicOwnerConsent(AddPublicPlotOwnerConsentDto addPublicPlotOwnerConsentDto, InstantProvider instantProvider) {
        PublicOwnerConsent publicOwnerConsent = PublicOwnerConsent.builder()
                .publicOwnerConsentId(addPublicPlotOwnerConsentDto.getPublicPlotOwnerConsentId())
                .ownerName(addPublicPlotOwnerConsentDto.getOwnerName())
                .plotNumber(addPublicPlotOwnerConsentDto.getPlotNumber())
                .ownerAddress(Address.of(addPublicPlotOwnerConsentDto.getStreet(),
                        addPublicPlotOwnerConsentDto.getHouseNumber(),
                        addPublicPlotOwnerConsentDto.getApartmentNumber(),
                        addPublicPlotOwnerConsentDto.getPostalCode(),
                        addPublicPlotOwnerConsentDto.getCity()))
                .consentCreateDate(instantProvider.now())
                .consentStatus(ConsentStatus.CONSENT_CREATED)
                .collectorName(addPublicPlotOwnerConsentDto.getCollectorName())
                .build();
        publicOwnerConsents.add(publicOwnerConsent);
        return publicOwnerConsent;
    }

    void acceptPrivatePlotOwnerConsent(UUID privatePlotOwnerConsentId, InstantProvider instantProvider) {
        privatePlotOwnerConsents.stream()
                .filter(privatePlotOwnerConsent -> privatePlotOwnerConsent.getPrivatePlotOwnerConsentId().equals(privatePlotOwnerConsentId))
                .findFirst()
                .ifPresent(privatePlotOwnerConsent -> privatePlotOwnerConsent.accept(instantProvider.now()));
    }



    public void acceptPublicOwnerConsent(UUID publicPlotOwnerConsentId, InstantProvider instantProvider) {
        publicOwnerConsents.stream()
                .filter(publicOwnerConsent -> publicOwnerConsent.getPublicOwnerConsentId().equals(publicPlotOwnerConsentId))
                .findFirst()
                .ifPresent(publicOwnerConsent -> publicOwnerConsent.accept(instantProvider.now()));
    }

    ConsentsDto dto() {
        return ConsentsDto.builder()
                .consentId(consentId)
                .deadline(deadline)
                .requestForPlotExtractsSent(requestForPlotExtractsSent)
                .privatePlotOwnerConsents(privatePlotOwnerConsents.stream().map(PrivatePlotOwnerConsent::dto).toList())
                .publicOwnerConsents(publicOwnerConsents.stream().map(PublicOwnerConsent::dto).toList())
                .build();
    }
}
