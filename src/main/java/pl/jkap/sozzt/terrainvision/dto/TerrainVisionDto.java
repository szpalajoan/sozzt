package pl.jkap.sozzt.terrainvision.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TerrainVisionDto {

    public enum TerrainVisionStatus {
        HOLD,
        IN_PROGRESS,
        COMPLETED
    }

    public enum RoutePreparationNeed {
        NONE,
        NECESSARY,
        NOT_NEED
    }

    private UUID terrainVisionId;
    private boolean allPhotosUploaded;
    private Instant deadline;
    private TerrainVisionStatus terrainVisionStatus;
    private RoutePreparationNeed routePreparationNeed;

}
