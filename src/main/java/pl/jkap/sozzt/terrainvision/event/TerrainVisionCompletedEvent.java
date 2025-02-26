package pl.jkap.sozzt.terrainvision.event;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.UUID;

@Getter
@AllArgsConstructor
public class TerrainVisionCompletedEvent {
    private final UUID contractId;
    private final boolean isProjectPurposesMapPreparationNeed;
}
