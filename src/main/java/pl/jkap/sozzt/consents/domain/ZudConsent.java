package pl.jkap.sozzt.consents.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.consents.dto.UpdateZudConsentDto;
import pl.jkap.sozzt.consents.dto.ZudConsentDto;
import pl.jkap.sozzt.instant.InstantProvider;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
class ZudConsent {
    private final UUID zudConsentId;
    private String institutionName;
    private String plotNumber;
    private String comment;
    private final Instant consentCreateDate;
    private ConsentStatus consentStatus;
    private Instant consentGivenDate;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;
    private String statusComment;

    void update(UpdateZudConsentDto updateZudConsentDto) {
        this.institutionName = updateZudConsentDto.getInstitutionName();
        this.plotNumber = updateZudConsentDto.getPlotNumber();
        this.comment = updateZudConsentDto.getComment();
        this.collectorName = updateZudConsentDto.getCollectorName();
        this.mailingDate = updateZudConsentDto.getMailingDate();
        this.deliveryType = updateZudConsentDto.getDeliveryType();
    }

    void markAsSentByMail(InstantProvider instantProvider) {
        this.consentStatus = ConsentStatus.SENT;
        this.mailingDate = instantProvider.now();
        this.deliveryType = DeliveryType.POST;
    }

    void accept(Instant now) {
        this.consentStatus = ConsentStatus.CONSENT_GIVEN;
        this.consentGivenDate = now;
    }

    void invalidate(String reason, InstantProvider instantProvider) {
        this.consentStatus = ConsentStatus.INVALIDATED;
        this.statusComment = reason;
        this.consentGivenDate = instantProvider.now();
    }

    boolean isConsentAccepted() {
        return consentStatus == ConsentStatus.CONSENT_GIVEN;
    }

    ZudConsentDto dto() {
        return ZudConsentDto.builder()
                .zudConsentId(zudConsentId)
                .institutionName(institutionName)
                .plotNumber(plotNumber)
                .comment(comment)
                .consentCreateDate(consentCreateDate)
                .consentStatus(consentStatus)
                .consentGivenDate(consentGivenDate)
                .collectorName(collectorName)
                .mailingDate(mailingDate)
                .deliveryType(deliveryType)
                .statusComment(statusComment)
                .build();
    }
} 