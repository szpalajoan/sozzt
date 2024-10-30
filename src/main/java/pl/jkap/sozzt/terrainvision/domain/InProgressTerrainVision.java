package pl.jkap.sozzt.terrainvision.domain;

import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;
import pl.jkap.sozzt.terrainvision.exception.InvalidMapChangeException;

import java.time.Instant;
import java.util.UUID;

class InProgressTerrainVision extends TerrainVision {
    InProgressTerrainVision(UUID terrainVisionId, Instant deadline, MapChange mapChange) {
        super(terrainVisionId, false, deadline, TerrainVisionStatus.IN_PROGRESS, mapChange);
    }

    void confirmAllPhotosAreUploaded() {
       allPhotosUploaded = true;
    }


    void confirmChangesOnMap(TerrainVisionDto.MapChange mapChange) {
        MapChange newMapChange = MapChange.valueOf(mapChange.name());
        if(MapChange.NONE == newMapChange) {
            throw new InvalidMapChangeException("Map change cannot be NONE");
        }
        this.mapChange = newMapChange;
    }
}
