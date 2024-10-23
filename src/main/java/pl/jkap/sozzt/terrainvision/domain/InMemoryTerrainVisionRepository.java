package pl.jkap.sozzt.terrainvision.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.Optional;
import java.util.UUID;

class InMemoryTerrainVisionRepository extends InMemoryRepository<TerrainVision, UUID> implements TerrainVisionRepository {
    @Override
    protected <S extends TerrainVision> UUID getIdFromEntity(S entity) {
        return entity.getTerrainVisionId();
    }

    @Override
    public Optional<HoldTerrainVision> findHoldTerrainVisionById(UUID terrainVisionId) {
        return table.values().stream()
                .filter(terrainVision -> TerrainVision.TerrainVisionStatus.HOLD.equals(terrainVision.getTerrainVisionStatus()))
                .map(terrainVision -> (HoldTerrainVision) terrainVision)
                .findFirst();
    }

    @Override
    public Optional<InProgressTerrainVision> findInProgressTerrainVisionById(UUID terrainVisionId) {
        return table.values().stream()
                .filter(terrainVision -> TerrainVision.TerrainVisionStatus.IN_PROGRESS.equals(terrainVision.getTerrainVisionStatus()))
                .map(terrainVision -> (InProgressTerrainVision) terrainVision)
                .findFirst();
    }
}
