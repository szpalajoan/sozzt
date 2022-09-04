package pl.jkap.sozzt.consent.event;

import java.util.UUID;

public class ConsentRejectSpringEvent extends ConsentSpringEvent {
    public ConsentRejectSpringEvent(UUID idContract) {
        super(idContract);
    }
}