package pl.jkap.sozzt.consents.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class BeginConsentsCollectionEvent {
    private final UUID contractId;
}
