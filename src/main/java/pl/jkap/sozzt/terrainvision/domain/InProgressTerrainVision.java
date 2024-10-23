package pl.jkap.sozzt.terrainvision.domain;

import java.time.Instant;
import java.util.UUID;

class InProgressTerrainVision extends TerrainVision {
    InProgressTerrainVision(UUID terrainVisionId, Instant deadline) {
        super(terrainVisionId, false, deadline, TerrainVisionStatus.IN_PROGRESS);
    }

    void confirmAllPhotosAreUploaded() {
       allPhotosUploaded = true;
    }
}
