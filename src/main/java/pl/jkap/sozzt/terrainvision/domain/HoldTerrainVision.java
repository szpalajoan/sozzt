package pl.jkap.sozzt.terrainvision.domain;

import java.time.Instant;
import java.util.UUID;

class HoldTerrainVision extends TerrainVision {
    HoldTerrainVision(UUID terrainVisionId, Instant deadline) {
        super(terrainVisionId, false, deadline, TerrainVisionStatus.HOLD);
    }

    InProgressTerrainVision begin() {
        return new InProgressTerrainVision(terrainVisionId, deadline);
    }
}
