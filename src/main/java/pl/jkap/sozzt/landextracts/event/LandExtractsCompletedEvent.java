package pl.jkap.sozzt.landextracts.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class LandExtractsCompletedEvent {
    private final UUID contractId;
} 