package pl.jkap.sozzt.consent.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public abstract class ConsentSpringEvent {
    private UUID idContract;
}
