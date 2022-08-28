package pl.jkap.sozzt.consent.domain;

import pl.jkap.sozzt.consent.dto.ConsentDto;

import java.util.UUID;

import static java.util.Objects.requireNonNull;

public class ConsentCreator {
    public Consent createConsent(ConsentDto consentDto) {
        requireNonNull(consentDto);
        return Consent.builder()
                .id(UUID.randomUUID())
                .contact(consentDto.getContact())
                .comment(consentDto.getComment())
                .status(ConsentStatus.WAITING)
                .idContract(consentDto.getIdContract())
                .location(consentDto.getLocation())
                .build();
    }
}
