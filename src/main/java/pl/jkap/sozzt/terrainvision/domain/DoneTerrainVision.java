package pl.jkap.sozzt.terrainvision.domain;

import java.time.Instant;
import java.util.UUID;

class DoneTerrainVision extends TerrainVision {

    DoneTerrainVision(UUID terrainVisionId, Instant deadline, MapChange mapChange) {
        super(terrainVisionId, true, deadline, TerrainVisionStatus.DONE, mapChange);
    }
}
