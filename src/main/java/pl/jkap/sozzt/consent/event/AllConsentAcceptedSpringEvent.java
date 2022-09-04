package pl.jkap.sozzt.consent.event;

import java.util.UUID;

public class AllConsentAcceptedSpringEvent extends ConsentSpringEvent {
    public AllConsentAcceptedSpringEvent(UUID idContract) {
        super(idContract);
    }
}
