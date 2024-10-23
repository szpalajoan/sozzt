package pl.jkap.sozzt.terrainvision.domain;

import java.time.Instant;
import java.util.UUID;

class DoneTerrainVision extends TerrainVision {

    DoneTerrainVision(UUID terrainVisionId, Instant deadline) {
        super(terrainVisionId, true, deadline, TerrainVisionStatus.DONE);
    }
}
