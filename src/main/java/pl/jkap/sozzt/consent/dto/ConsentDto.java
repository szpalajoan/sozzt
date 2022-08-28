package pl.jkap.sozzt.consent.dto;

import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.consent.domain.ConsentStatus;

import java.util.UUID;

@Builder
@Getter
public class ConsentDto {
    private UUID id;
    private UUID idContract;
    private String location;
    private String contact;
    private ConsentStatus status;
    private String comment;
}
