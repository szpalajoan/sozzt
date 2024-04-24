package pl.jkap.sozzt.terrainvision.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
class TerrainVision {

    @Id
    private UUID terrainVisionId;
    private UUID contractId;
    private boolean isPreliminaryMapUploaded;
    private String googleMapUrl;
    private boolean allPhotosUploaded;
    private Instant deadLine;
}
