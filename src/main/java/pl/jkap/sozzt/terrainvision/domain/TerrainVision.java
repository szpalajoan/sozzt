package pl.jkap.sozzt.terrainvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@AllArgsConstructor
abstract class TerrainVision {

    enum TerrainVisionStatus {
        HOLD,
        IN_PROGRESS,
        COMPLETED
    }

    enum RoutePreparationNeed {
        NONE,
        NECESSARY,
        NOT_NEED
    }

    @Id
    protected UUID terrainVisionId;
    protected boolean allPhotosUploaded;
    protected Instant deadline;
    protected TerrainVisionStatus terrainVisionStatus;
    protected RoutePreparationNeed routePreparationNeed;



    public TerrainVisionDto dto() {
        return TerrainVisionDto.builder()
                .terrainVisionId(terrainVisionId)
                .allPhotosUploaded(allPhotosUploaded)
                .deadline(deadline)
                .terrainVisionStatus(TerrainVisionDto.TerrainVisionStatus.valueOf(terrainVisionStatus.name()))
                .routePreparationNeed(TerrainVisionDto.RoutePreparationNeed.valueOf(routePreparationNeed.name()))
                .build();
    }
}
