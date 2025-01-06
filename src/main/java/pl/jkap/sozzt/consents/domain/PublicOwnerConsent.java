package pl.jkap.sozzt.consents.domain;

import jakarta.persistence.Entity;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.consents.dto.PublicPlotOwnerConsentDto;
import pl.jkap.sozzt.consents.dto.UpdatePublicPlotOwnerConsentDto;
import pl.jkap.sozzt.consents.exception.ConsentUpdateException;
import pl.jkap.sozzt.instant.InstantProvider;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@ToString
class PublicOwnerConsent {

    private UUID publicOwnerConsentId;
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


    void update(UpdatePublicPlotOwnerConsentDto updatePublicPlotOwnerConsentDto) {
        this.publicOwnerName = updatePublicPlotOwnerConsentDto.getPublicOwnerName();
        this.comment = updatePublicPlotOwnerConsentDto.getComment();
        this.plotNumber = updatePublicPlotOwnerConsentDto.getPlotNumber();
        this.collectorName = updatePublicPlotOwnerConsentDto.getCollectorName();
        this.mailingDate = updatePublicPlotOwnerConsentDto.getMailingDate();
        this.deliveryType = updatePublicPlotOwnerConsentDto.getDeliveryType().orElseThrow(() -> new ConsentUpdateException("Invalid delivery type - can not be null"));
    }
    void accept(Instant now) {
        this.consentGivenDate = now;
        this.consentStatus = ConsentStatus.CONSENT_GIVEN;
    }

    boolean isConsentAccepted() {
        return ConsentStatus.CONSENT_GIVEN.equals(consentStatus);
    }

    void markAsSentByMail(InstantProvider instantProvider) {
        this.consentStatus = ConsentStatus.SENT;
        this.deliveryType = DeliveryType.POST;
        this.mailingDate = instantProvider.now();
    }

    void invalidate(String reason, InstantProvider instantProvider) {
        this.consentStatus = ConsentStatus.INVALIDATED;
        this.statusComment = reason;
        this.consentGivenDate = instantProvider.now();
    }

    PublicPlotOwnerConsentDto dto() {
        return PublicPlotOwnerConsentDto.builder()
                .publicPlotOwnerConsentId(publicOwnerConsentId)
                .publicOwnerName(publicOwnerName)
                .comment(comment)
                .plotNumber(plotNumber)
                .consentCreateDate(consentCreateDate)
                .consentGivenDate(consentGivenDate)
                .consentStatus(consentStatus)
                .statusComment(statusComment)
                .collectorName(collectorName)
                .mailingDate(mailingDate)
                .deliveryType(deliveryType)
                .build();
    }
}
