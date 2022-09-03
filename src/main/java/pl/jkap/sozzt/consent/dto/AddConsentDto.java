package pl.jkap.sozzt.consent.dto;

import lombok.Builder;
import lombok.Getter;

import java.util.UUID;

@Getter
@Builder
public class AddConsentDto {
    private UUID idContract;
    private String location;
    private String contact;
}
