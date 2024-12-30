package pl.jkap.sozzt.consents.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto;
import pl.jkap.sozzt.consents.dto.UpdatePrivatePlotOwnerConsentDto;
import pl.jkap.sozzt.consents.exception.ConsentUpdateException;
import pl.jkap.sozzt.instant.InstantProvider;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@ToString
class PrivatePlotOwnerConsent {

    private UUID privatePlotOwnerConsentId;
    private String ownerName;
    private String comment;
    private String plotNumber;
    private Instant consentCreateDate;
    private Instant consentGivenDate;
    private ConsentStatus consentStatus;
    private String statusComment;
    private String collectorName;
    private Instant mailingDate;
    private DeliveryType deliveryType;


    void accept(Instant now) {
        this.consentGivenDate = now;
        this.consentStatus = ConsentStatus.CONSENT_GIVEN;
    }

    boolean isConsentAccepted() {
        return ConsentStatus.CONSENT_GIVEN.equals(consentStatus);
    }

    void update(UpdatePrivatePlotOwnerConsentDto updatePrivatePlotOwnerConsentDto) {
        this.ownerName = updatePrivatePlotOwnerConsentDto.getOwnerName();
        this.comment = updatePrivatePlotOwnerConsentDto.getComment();
        this.plotNumber = updatePrivatePlotOwnerConsentDto.getPlotNumber();
        this.collectorName = updatePrivatePlotOwnerConsentDto.getCollectorName();
        this.mailingDate = updatePrivatePlotOwnerConsentDto.getMailingDate();
        this.deliveryType = updatePrivatePlotOwnerConsentDto.getDeliveryType()
                .orElseThrow(() -> new ConsentUpdateException("Invalid delivery type - can not be null"));
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

    PrivatePlotOwnerConsentDto dto() {
        return PrivatePlotOwnerConsentDto.builder()
                .privatePlotOwnerConsentId(privatePlotOwnerConsentId)
                .ownerName(ownerName)
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
