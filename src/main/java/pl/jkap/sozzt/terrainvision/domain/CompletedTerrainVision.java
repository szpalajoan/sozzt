package pl.jkap.sozzt.terrainvision.domain;

import java.time.Instant;
import java.util.UUID;

class CompletedTerrainVision extends TerrainVision {

    CompletedTerrainVision(UUID terrainVisionId, Instant deadline, MapChange mapChange) {
        super(terrainVisionId, true, deadline, TerrainVisionStatus.COMPLETED, mapChange);
    }
}
