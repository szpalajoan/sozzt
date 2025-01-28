package pl.jkap.sozzt.consents.dto;

import lombok.*;
import pl.jkap.sozzt.consents.domain.DeliveryType;

import java.time.Instant;
import java.util.Optional;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class InvalidatePrivatePlotOwnerConsentDto {
    private String reason;
}
