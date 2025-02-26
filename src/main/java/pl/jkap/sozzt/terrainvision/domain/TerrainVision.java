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

    @Id
    protected UUID terrainVisionId;
    protected boolean allPhotosUploaded;
    protected Instant deadline;
    protected TerrainVisionStatus terrainVisionStatus;
    protected ProjectPurposesMapPreparationNeed projectPurposesMapPreparationNeed;



    public TerrainVisionDto dto() {
        return TerrainVisionDto.builder()
                .terrainVisionId(terrainVisionId)
                .allPhotosUploaded(allPhotosUploaded)
                .deadline(deadline)
                .terrainVisionStatus(TerrainVisionDto.TerrainVisionStatus.valueOf(terrainVisionStatus.name()))
                .projectPurposesMapPreparationNeed(projectPurposesMapPreparationNeed)
                .build();
    }
}
