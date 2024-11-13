package pl.jkap.sozzt.terrainvision.event;

import lombok.Getter;

import java.util.UUID;

@Getter
public class TerrainVisionCompletedEvent {
    private final UUID contractId;

    public TerrainVisionCompletedEvent(UUID contractId) {
        this.contractId = contractId;
    }
}
