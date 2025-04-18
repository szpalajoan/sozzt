package pl.jkap.sozzt.terrainvision.dto;

import lombok.*;
import pl.jkap.sozzt.terrainvision.domain.ProjectPurposesMapPreparationNeed;

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

    private UUID terrainVisionId;
    private boolean allPhotosUploaded;
    private Instant deadline;
    private TerrainVisionStatus terrainVisionStatus;
    private ProjectPurposesMapPreparationNeed projectPurposesMapPreparationNeed;

}
