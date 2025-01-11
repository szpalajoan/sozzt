package pl.jkap.sozzt.terrainvision.domain;

import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;
import pl.jkap.sozzt.terrainvision.exception.CompletionTerrainVisionException;
import pl.jkap.sozzt.terrainvision.exception.InvalidRoutePreparationNeedException;

import java.time.Instant;
import java.util.UUID;

class InProgressTerrainVision extends TerrainVision {
    InProgressTerrainVision(UUID terrainVisionId, Instant deadline, RoutePreparationNeed routePreparationNeed) {
        super(terrainVisionId, false, deadline, TerrainVisionStatus.IN_PROGRESS, routePreparationNeed);
    }

    void confirmAllPhotosAreUploaded() {
       allPhotosUploaded = true;
    }


    void setRoutePreparationNeed(TerrainVisionDto.RoutePreparationNeed routePreparationNeed) {
        RoutePreparationNeed newRoutePreparationNeed = TerrainVision.RoutePreparationNeed.valueOf(routePreparationNeed.name());
        if(TerrainVision.RoutePreparationNeed.NONE == newRoutePreparationNeed) {
            throw new InvalidRoutePreparationNeedException("Route preparation need has to be set");
        }
        this.routePreparationNeed = newRoutePreparationNeed;
    }

    CompletedTerrainVision complete() {
        if(!allPhotosUploaded) {
            throw new CompletionTerrainVisionException("All photos must be uploaded");
        }
        if(TerrainVision.RoutePreparationNeed.NONE == routePreparationNeed) {
            throw new InvalidRoutePreparationNeedException("Route preparation need has to be set");
        }
        return new CompletedTerrainVision(terrainVisionId, deadline, routePreparationNeed);
    }
}
