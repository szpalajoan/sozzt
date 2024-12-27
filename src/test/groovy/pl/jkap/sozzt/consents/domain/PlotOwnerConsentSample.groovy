package pl.jkap.sozzt.consents.domain

import pl.jkap.sozzt.consents.dto.AddPrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.AddPublicPlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PrivatePlotOwnerConsentDto
import pl.jkap.sozzt.consents.dto.PublicOwnerConsentDto
import pl.jkap.sozzt.instant.InstantSamples
import pl.jkap.sozzt.sample.SampleModifier
import pl.jkap.sozzt.user.UserSample

trait PlotOwnerConsentSample implements UserSample, InstantSamples {


    PrivatePlotOwnerConsentDto KRYNICA_PRIVATE_PLOT_OWNER_CONSENT = PrivatePlotOwnerConsentDto.builder()
            .privatePlotOwnerConsentId(UUID.fromString("827d54cf-badc-4f84-9cf0-f7637f3d26e0"))
            .ownerName("Marcin Krynicki")
            .street("Kwiatowa")
            .houseNumber("1")
            .apartmentNumber("12")
            .postalCode("12-345")
            .city("Krynica")
            .plotNumber("123")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.CONSENT_CREATED)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .build()

    PublicOwnerConsentDto KRYNICA_PUBLIC_OWNER_CONSENT = PublicOwnerConsentDto.builder()
            .publicPlotOwnerConsentId(UUID.fromString("65082b5f-2656-4e2e-a7f5-b3c0800c3b0f"))
            .publicOwnerName("Gmina Krynica")
            .street("Urzędowa")
            .houseNumber("2")
            .postalCode("12-345")
            .city("Krynica")
            .plotNumber("8573/3")
            .consentCreateDate(NOW)
            .consentStatus(ConsentStatus.CONSENT_CREATED)
            .collectorName(IWONA_CONSENT_COLLECTOR.name)
            .build()


    AddPrivatePlotOwnerConsentDto toAddPrivatePlotOwnerConsent(PrivatePlotOwnerConsentDto privatePlotOwnerConsentDto) {
        return AddPrivatePlotOwnerConsentDto.builder()
                .privatePlotOwnerConsentId(privatePlotOwnerConsentDto.privatePlotOwnerConsentId)
                .ownerName(privatePlotOwnerConsentDto.ownerName)
                .street(privatePlotOwnerConsentDto.street)
                .houseNumber(privatePlotOwnerConsentDto.houseNumber)
                .apartmentNumber(privatePlotOwnerConsentDto.apartmentNumber)
                .postalCode(privatePlotOwnerConsentDto.postalCode)
                .city(privatePlotOwnerConsentDto.city)
                .plotNumber(privatePlotOwnerConsentDto.plotNumber)
                .collectorName(privatePlotOwnerConsentDto.collectorName)
                .build()
    }

    AddPublicPlotOwnerConsentDto toAddPublicPlotOwnerConsent(PublicOwnerConsentDto publicOwnerConsentDto) {
        return AddPublicPlotOwnerConsentDto.builder()
                .publicPlotOwnerConsentId(publicOwnerConsentDto.publicPlotOwnerConsentId)
                .ownerName(publicOwnerConsentDto.publicOwnerName)
                .street(publicOwnerConsentDto.street)
                .houseNumber(publicOwnerConsentDto.houseNumber)
                .postalCode(publicOwnerConsentDto.postalCode)
                .city(publicOwnerConsentDto.city)
                .plotNumber(publicOwnerConsentDto.plotNumber)
                .collectorName(publicOwnerConsentDto.collectorName)
                .build()
    }

    PrivatePlotOwnerConsentDto with(PrivatePlotOwnerConsentDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(PrivatePlotOwnerConsentDto.class, consentsDto, properties)
    }

    PublicOwnerConsentDto with(PublicOwnerConsentDto consentsDto, Map<String, Object> properties) {
        return SampleModifier.with(PublicOwnerConsentDto.class, consentsDto, properties)
    }
}