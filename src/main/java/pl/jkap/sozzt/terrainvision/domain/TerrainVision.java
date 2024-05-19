package pl.jkap.sozzt.terrainvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
class TerrainVision {

    @Id
    private UUID terrainVisionId;
    private boolean allPhotosUploaded;
    private Instant deadline;


    public TerrainVisionDto dto() {
        return TerrainVisionDto.builder()
                .terrainVisionId(terrainVisionId)
                .allPhotosUploaded(allPhotosUploaded)
                .deadline(deadline)
                .build();
    }
}
