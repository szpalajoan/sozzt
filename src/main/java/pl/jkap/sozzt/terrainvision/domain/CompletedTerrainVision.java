package pl.jkap.sozzt.terrainvision.domain;

import java.time.Instant;
import java.util.UUID;

class CompletedTerrainVision extends TerrainVision {

    CompletedTerrainVision(UUID terrainVisionId, Instant deadline, RoutePreparationNeed routePreparationNeed) {
        super(terrainVisionId, true, deadline, TerrainVisionStatus.COMPLETED, routePreparationNeed);
    }
}
