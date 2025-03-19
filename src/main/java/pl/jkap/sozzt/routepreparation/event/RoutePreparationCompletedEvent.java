package pl.jkap.sozzt.routepreparation.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class RoutePreparationCompletedEvent {
    private final UUID contractId;

    public RoutePreparationCompletedEvent(UUID contractId) {
        this.contractId = contractId;
    }
}
