package pl.jkap.sozzt.consent.dto;

import lombok.Getter;

import java.util.UUID;

@Getter
public class AddConsentDto {
    private UUID idContract;
    private String location;
    private String contact;
}
