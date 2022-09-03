package pl.jkap.sozzt.consent.domain;

import pl.jkap.sozzt.consent.dto.AddConsentDto;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

class ConsentCreator {
    public Consent createConsent(AddConsentDto addConsentDto) {
        requireNonNull(addConsentDto);
        return Consent.builder()
                .id(UUID.randomUUID())
                .contact(addConsentDto.getContact())
                .status(ConsentStatus.WAITING)
                .idContract(addConsentDto.getIdContract())
                .location(addConsentDto.getLocation())
                .build();
    }
}
