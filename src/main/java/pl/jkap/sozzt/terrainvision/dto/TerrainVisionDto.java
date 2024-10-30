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
        DONE
    }

    public enum MapChange {
        NONE,
        MODIFIED,
        NOT_NECESSARY
    }

    private UUID terrainVisionId;
    private boolean allPhotosUploaded;
    private Instant deadline;
    private TerrainVisionStatus terrainVisionStatus;
    private MapChange mapChange;

}
