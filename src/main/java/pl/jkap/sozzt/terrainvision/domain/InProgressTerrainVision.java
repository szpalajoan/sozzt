package pl.jkap.sozzt.terrainvision.domain;

import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;
import pl.jkap.sozzt.terrainvision.exception.CompletionTerrainVisionException;
import pl.jkap.sozzt.terrainvision.exception.InvalidMapChangeException;

import java.time.Instant;
import java.util.UUID;

class InProgressTerrainVision extends TerrainVision {
    InProgressTerrainVision(UUID terrainVisionId, Instant deadline, RoutePreparation routePreparation) {
        super(terrainVisionId, false, deadline, TerrainVisionStatus.IN_PROGRESS, routePreparation);
    }

    void confirmAllPhotosAreUploaded() {
       allPhotosUploaded = true;
    }


    void setRoutePreparationNecessary(TerrainVisionDto.RoutePreparation routePreparation) {
        RoutePreparation newRoutePreparation = RoutePreparation.valueOf(routePreparation.name());
        if(RoutePreparation.NONE == newRoutePreparation) {
            throw new InvalidMapChangeException("Map change cannot be NONE");
        }
        this.routePreparation = newRoutePreparation;
    }

    CompletedTerrainVision complete() {
        if(!allPhotosUploaded) {
            throw new CompletionTerrainVisionException("All photos must be uploaded");
        }
        if(RoutePreparation.NONE == routePreparation) {
            throw new CompletionTerrainVisionException("Map change cannot be NONE");
        }
        return new CompletedTerrainVision(terrainVisionId, deadline, routePreparation);
    }
}
