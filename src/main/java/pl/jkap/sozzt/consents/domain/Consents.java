package pl.jkap.sozzt.consents.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.consents.dto.*;
import pl.jkap.sozzt.consents.exception.ConsentUpdateException;
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
    private Collection<PrivatePlotOwnerConsent> privatePlotOwnerConsents;
    private Collection<PublicOwnerConsent> publicOwnerConsents;
    private boolean isCompleted;
    private ZudConsent zudConsent;

    PrivatePlotOwnerConsent addPrivatePlotOwnerConsent(AddPrivatePlotOwnerConsentDto addPrivatePlotOwnerConsentDto, InstantProvider instantProvider) {
        PrivatePlotOwnerConsent consent = PrivatePlotOwnerConsent.builder()
                .privatePlotOwnerConsentId(addPrivatePlotOwnerConsentDto.getPrivatePlotOwnerConsentId().orElse(UUID.randomUUID()))
                .ownerName(addPrivatePlotOwnerConsentDto.getOwnerName())
                .comment(addPrivatePlotOwnerConsentDto.getComment())
                .plotNumber(addPrivatePlotOwnerConsentDto.getPlotNumber())
                .consentCreateDate(instantProvider.now())
                .consentStatus(ConsentStatus.CONSENT_CREATED)
                .collectorName(addPrivatePlotOwnerConsentDto.getCollectorName())
                .deliveryType(addPrivatePlotOwnerConsentDto.getDeliveryType().orElse(DeliveryType.NOT_SPECIFIED))
                .build();
        privatePlotOwnerConsents.add(consent);
        return consent;
    }

    PublicOwnerConsent addPublicOwnerConsent(AddPublicPlotOwnerConsentDto addPublicPlotOwnerConsentDto, InstantProvider instantProvider) {
        PublicOwnerConsent publicOwnerConsent = PublicOwnerConsent.builder()
                .publicOwnerConsentId(addPublicPlotOwnerConsentDto.getPublicPlotOwnerConsentId().orElse(UUID.randomUUID()))
                .publicOwnerName(addPublicPlotOwnerConsentDto.getOwnerName())
                .plotNumber(addPublicPlotOwnerConsentDto.getPlotNumber())
                .comment(addPublicPlotOwnerConsentDto.getComment())
                .consentCreateDate(instantProvider.now())
                .consentStatus(ConsentStatus.CONSENT_CREATED)
                .collectorName(addPublicPlotOwnerConsentDto.getCollectorName())
                .deliveryType(addPublicPlotOwnerConsentDto.getDeliveryType().orElse(DeliveryType.NOT_SPECIFIED))
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

    void acceptPublicOwnerConsent(UUID publicPlotOwnerConsentId, InstantProvider instantProvider) {
        publicOwnerConsents.stream()
                .filter(publicOwnerConsent -> publicOwnerConsent.getPublicOwnerConsentId().equals(publicPlotOwnerConsentId))
                .findFirst()
                .ifPresent(publicOwnerConsent -> publicOwnerConsent.accept(instantProvider.now()));
    }

    void updatePrivatePlotOwnerConsent(UUID privatePlotOwnerConsentId, UpdatePrivatePlotOwnerConsentDto updatePrivatePlotOwnerConsentDto) {
        privatePlotOwnerConsents.stream()
                .filter(privatePlotOwnerConsent -> privatePlotOwnerConsent.getPrivatePlotOwnerConsentId().equals(privatePlotOwnerConsentId))
                .findFirst()
                .ifPresent(privatePlotOwnerConsent -> privatePlotOwnerConsent.update(updatePrivatePlotOwnerConsentDto));
    }

    void updatePublicPlotOwnerConsent(UUID publicPlotOwnerConsentId, UpdatePublicPlotOwnerConsentDto updatePublicPlotOwnerConsentDto) {
        publicOwnerConsents.stream()
                .filter(publicOwnerConsent -> publicOwnerConsent.getPublicOwnerConsentId().equals(publicPlotOwnerConsentId))
                .findFirst()
                .ifPresent(publicOwnerConsent -> publicOwnerConsent.update(updatePublicPlotOwnerConsentDto));
    }

    void markPrivatePlotOwnerConsentAsSentByMail(UUID privatePlotOwnerConsentId, InstantProvider instantProvider) {
        privatePlotOwnerConsents.stream()
                .filter(privatePlotOwnerConsent -> privatePlotOwnerConsent.getPrivatePlotOwnerConsentId().equals(privatePlotOwnerConsentId))
                .findFirst()
                .ifPresent(privatePlotOwnerConsent -> privatePlotOwnerConsent.markAsSentByMail(instantProvider));
    }

    void markPublicPlotOwnerConsentAsSentByMail(UUID publicPlotOwnerConsentId, InstantProvider instantProvider) {
        publicOwnerConsents.stream()
                .filter(publicOwnerConsent -> publicOwnerConsent.getPublicOwnerConsentId().equals(publicPlotOwnerConsentId))
                .findFirst()
                .ifPresent(publicOwnerConsent -> publicOwnerConsent.markAsSentByMail(instantProvider));
    }

    void invalidatePrivatePlotOwnerConsent(UUID privatePlotOwnerConsentId, String reason, InstantProvider instantProvider) {
        privatePlotOwnerConsents.stream()
                .filter(privatePlotOwnerConsent -> privatePlotOwnerConsent.getPrivatePlotOwnerConsentId().equals(privatePlotOwnerConsentId))
                .findFirst()
                .ifPresent(privatePlotOwnerConsent -> privatePlotOwnerConsent.invalidate(reason, instantProvider));
    }

    void invalidatePublicPlotOwnerConsent(UUID publicPlotOwnerConsentId, String reason, InstantProvider instantProvider) {
        publicOwnerConsents.stream()
                .filter(publicOwnerConsent -> publicOwnerConsent.getPublicOwnerConsentId().equals(publicPlotOwnerConsentId))
                .findFirst()
                .ifPresent(publicOwnerConsent -> publicOwnerConsent.invalidate(reason, instantProvider));
    }

    void markAsCompleted() {
        if (!isReadyForCompletion()) {
            throw new IllegalStateException("Consents collection cannot be completed.");
        }
        isCompleted = true;
    }

    void updateZudConsent(UpdateZudConsentDto updateZudConsentDto) {
        if(zudConsent == null) {
            throw new ConsentUpdateException("ZudConsent not exist");
        }
        zudConsent.update(updateZudConsentDto);
    }

    void acceptZudConsent(InstantProvider instantProvider) {
        if(zudConsent == null) {
            throw new ConsentUpdateException("ZudConsent not exist");
        }
        zudConsent.accept(instantProvider.now());
    }

    void markZudConsentAsSentByMail(InstantProvider instantProvider) {
        if(zudConsent == null) {
            throw new ConsentUpdateException("ZudConsent not exist");
        }
        zudConsent.markAsSentByMail(instantProvider);
    }

    void invalidateZudConsent(String reason, InstantProvider instantProvider) {
        if(zudConsent == null) {
            throw new ConsentUpdateException("ZudConsent not exist");
        }
        zudConsent.invalidate(reason, instantProvider);
    }

    private boolean isReadyForCompletion() {
        boolean isZudCompleted = zudConsent == null || zudConsent.isConsentAccepted();
        return privatePlotOwnerConsents.stream().allMatch(PrivatePlotOwnerConsent::isConsentAccepted)
                && publicOwnerConsents.stream().allMatch(PublicOwnerConsent::isConsentAccepted)
                && isZudCompleted;
    }

    ConsentsDto dto() {
        return ConsentsDto.builder()
                .consentId(consentId)
                .deadline(deadline)
                .privatePlotOwnerConsents(privatePlotOwnerConsents.stream().map(PrivatePlotOwnerConsent::dto).toList())
                .publicOwnerConsents(publicOwnerConsents.stream().map(PublicOwnerConsent::dto).toList())
                .zudConsent(zudConsent == null ? null : zudConsent.dto())
                .completed(isCompleted)
                .build();
    }
}
