package pl.jkap.sozzt.terrainvision.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.Optional;
import java.util.UUID;

interface TerrainVisionRepository extends CrudRepository<TerrainVision, UUID> {
    Optional<HoldTerrainVision> findHoldTerrainVisionById(UUID terrainVisionId);

    Optional<InProgressTerrainVision> findInProgressTerrainVisionById(UUID terrainVisionId);
}
