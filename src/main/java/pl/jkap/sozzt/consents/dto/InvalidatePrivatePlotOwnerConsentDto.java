package pl.jkap.sozzt.consents.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class InvalidatePrivatePlotOwnerConsentDto {
    private String reason;
}
