package pl.jkap.sozzt.consents.dto;

import lombok.*;

import java.time.Instant;
import java.util.Collection;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class ConsentsDto {

    private UUID consentId;
    private Instant deadline;
    private Collection<PrivatePlotOwnerConsentDto> privatePlotOwnerConsents;
    private Collection<PublicPlotOwnerConsentDto> publicOwnerConsents;
    private boolean completed;
    private ZudConsentDto zudConsent;
}
