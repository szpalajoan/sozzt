package pl.jkap.sozzt.preliminaryplanning.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class PreliminaryPlanCompletedEvent {
    private final UUID contractId;

    public PreliminaryPlanCompletedEvent(UUID contractId) {
        this.contractId = contractId;
    }
}
