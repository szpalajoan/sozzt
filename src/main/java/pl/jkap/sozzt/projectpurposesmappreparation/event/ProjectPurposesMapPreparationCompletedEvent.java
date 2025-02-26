package pl.jkap.sozzt.projectpurposesmappreparation.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class ProjectPurposesMapPreparationCompletedEvent {
    private final UUID contractId;

    public ProjectPurposesMapPreparationCompletedEvent(UUID contractId) {
        this.contractId = contractId;
    }
}
