package pl.jkap.sozzt.consent.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RejectConsentDto {
    UUID idConsent;
    String comment;
}
