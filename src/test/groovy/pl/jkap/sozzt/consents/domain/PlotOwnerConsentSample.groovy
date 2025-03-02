package pl.jkap.sozzt.consents.domain

import pl.jkap.sozzt.consents.dto.AddPrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.AddPublicPlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PublicPlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.UpdatePrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.UpdatePublicPlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.ZudConsentDto
import pl.jkap.sozzt.consents.dto.AddZudConsentDto
import pl.jkap.sozzt.consents.dto.UpdateZudConsentDto
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.user.UserSample

trait PlotOwnerConsentSample implements UserSample, InstantSamples {


    PrivatePlotOwnerConsentDto KRYNICA_PRIVATE_PLOT_OWNER_CONSENT = PrivatePlotOwnerConsentDto.builder()
            .privatePlotOwnerConsentId(UUID.fromString("827d54cf-badc-4f84-9cf0-f7637f3d26e0"))
            .ownerName("Marcin Krynicki")
            .plotNumber("123")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.CONSENT_CREATED)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .deliveryType(DeliveryType.NOT_SPECIFIED)
            .build()

    PublicPlotOwnerConsentDto KRYNICA_PUBLIC_OWNER_CONSENT = PublicPlotOwnerConsentDto.builder()
            .publicPlotOwnerConsentId(UUID.fromString("65082b5f-2656-4e2e-a7f5-b3c0800c3b0f"))
            .publicOwnerName("Gmina Krynica")
            .plotNumber("8573/3")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.CONSENT_CREATED)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .deliveryType(DeliveryType.NOT_SPECIFIED)
            .build()

    PrivatePlotOwnerConsentDto CONFIRMED_KRYNICA_PRIVATE_PLOT_OWNER_CONSENT = PrivatePlotOwnerConsentDto.builder()
            .privatePlotOwnerConsentId(UUID.fromString("827d54cf-badc-4f84-9cf0-f7637f3d26e0"))
            .ownerName("Marcin Krynicki")
            .plotNumber("123")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.CONSENT_GIVEN)
            .consentGivenDate(NOW)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .deliveryType(DeliveryType.NOT_SPECIFIED)
            .build()

    PublicPlotOwnerConsentDto CONFIRMED_PUBLIC_PLOT_OWNER_CONSENT = PublicPlotOwnerConsentDto.builder()
            .publicPlotOwnerConsentId(UUID.fromString("65082b5f-2656-4e2e-a7f5-b3c0800c3b0f"))
            .publicOwnerName("Gmina Krynica")
            .plotNumber("8573/3")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.CONSENT_GIVEN)
            .consentGivenDate(NOW)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .deliveryType(DeliveryType.NOT_SPECIFIED)
            .build()

    ZudConsentDto REQUIRED_EMPTY_ZUD_CONSENT = ZudConsentDto.builder()
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.REQUIRED)
            .build()

    ZudConsentDto KRYNICA_ZUD_CONSENT = ZudConsentDto.builder()
            .institutionName("ZUD Krynica")
            .plotNumber("8573/3")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.REQUIRED)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .deliveryType(DeliveryType.NOT_SPECIFIED)
            .build()

    ZudConsentDto CONFIRMED_KRYNICA_ZUD_CONSENT = ZudConsentDto.builder()
            .institutionName("ZUD Krynica")
            .plotNumber("8573/3")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.CONSENT_GIVEN)
            .consentGivenDate(NOW)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .deliveryType(DeliveryType.NOT_SPECIFIED)
            .build()

    AddPrivatePlotOwnerConsentDto toAddPrivatePlotOwnerConsent(PrivatePlotOwnerConsentDto privatePlotOwnerConsentDto) {
        return AddPrivatePlotOwnerConsentDto.builder()
                .privatePlotOwnerConsentId(privatePlotOwnerConsentDto.privatePlotOwnerConsentId)
                .ownerName(privatePlotOwnerConsentDto.ownerName)
                .plotNumber(privatePlotOwnerConsentDto.plotNumber)
                .collectorName(privatePlotOwnerConsentDto.collectorName)
                .deliveryType(privatePlotOwnerConsentDto.deliveryType)
                .comment(privatePlotOwnerConsentDto.comment)
                .mailingDate(privatePlotOwnerConsentDto.mailingDate)
                .build()
    }

    AddPublicPlotOwnerConsentDto toAddPublicPlotOwnerConsent(PublicPlotOwnerConsentDto publicOwnerConsentDto) {
        return AddPublicPlotOwnerConsentDto.builder()
                .publicPlotOwnerConsentId(publicOwnerConsentDto.publicPlotOwnerConsentId)
                .ownerName(publicOwnerConsentDto.publicOwnerName)
                .plotNumber(publicOwnerConsentDto.plotNumber)
                .collectorName(publicOwnerConsentDto.collectorName)
                .deliveryType(publicOwnerConsentDto.deliveryType)
                .comment(publicOwnerConsentDto.comment)
                .mailingDate(publicOwnerConsentDto.mailingDate)
                .build()
    }

    UpdatePrivatePlotOwnerConsentDto toUpdatePrivatePlotOwnerConsent(PrivatePlotOwnerConsentDto privatePlotOwnerConsentDto) {
        return UpdatePrivatePlotOwnerConsentDto.builder()
                .ownerName(privatePlotOwnerConsentDto.ownerName)
                .plotNumber(privatePlotOwnerConsentDto.plotNumber)
                .collectorName(privatePlotOwnerConsentDto.collectorName)
                .deliveryType(privatePlotOwnerConsentDto.deliveryType)
                .comment(privatePlotOwnerConsentDto.comment)
                .mailingDate(privatePlotOwnerConsentDto.mailingDate)
                .build()
    }

    UpdatePublicPlotOwnerConsentDto toUpdatePublicPlotOwnerConsent(PublicPlotOwnerConsentDto publicOwnerConsentDto) {
        return UpdatePublicPlotOwnerConsentDto.builder()
                .publicOwnerName(publicOwnerConsentDto.publicOwnerName)
                .plotNumber(publicOwnerConsentDto.plotNumber)
                .collectorName(publicOwnerConsentDto.collectorName)
                .deliveryType(publicOwnerConsentDto.deliveryType)
                .comment(publicOwnerConsentDto.comment)
                .mailingDate(publicOwnerConsentDto.mailingDate)
                .build()
    }

    AddZudConsentDto toAddZudConsent(ZudConsentDto zudConsentDto) {
        return AddZudConsentDto.builder()
                .zudConsentId(zudConsentDto.zudConsentId)
                .institutionName(zudConsentDto.institutionName)
                .plotNumber(zudConsentDto.plotNumber)
                .collectorName(zudConsentDto.collectorName)
                .deliveryType(zudConsentDto.deliveryType)
                .comment(zudConsentDto.comment)
                .mailingDate(zudConsentDto.mailingDate)
                .build()
    }

    UpdateZudConsentDto toUpdateZudConsent(ZudConsentDto zudConsentDto) {
        return UpdateZudConsentDto.builder()
                .institutionName(zudConsentDto.institutionName)
                .plotNumber(zudConsentDto.plotNumber)
                .collectorName(zudConsentDto.collectorName)
                .deliveryType(zudConsentDto.deliveryType)
                .comment(zudConsentDto.comment)
                .mailingDate(zudConsentDto.mailingDate)
                .build()
    }

    PrivatePlotOwnerConsentDto with(PrivatePlotOwnerConsentDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(PrivatePlotOwnerConsentDto.class, consentsDto, properties)
    }

    PublicPlotOwnerConsentDto with(PublicPlotOwnerConsentDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(PublicPlotOwnerConsentDto.class, consentsDto, properties)
    }

    UpdatePrivatePlotOwnerConsentDto with(UpdatePrivatePlotOwnerConsentDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(UpdatePrivatePlotOwnerConsentDto.class, consentsDto, properties)
    }

    UpdatePublicPlotOwnerConsentDto with(UpdatePublicPlotOwnerConsentDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(UpdatePublicPlotOwnerConsentDto.class, consentsDto, properties)
    }

    ZudConsentDto with(ZudConsentDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(ZudConsentDto.class, consentsDto, properties)
    }
}