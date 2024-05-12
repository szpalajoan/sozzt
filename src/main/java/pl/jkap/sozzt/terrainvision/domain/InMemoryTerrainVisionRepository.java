package pl.jkap.sozzt.terrainvision.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

class InMemoryTerrainVisionRepository extends InMemoryRepository<TerrainVision, UUID> implements TerrainVisionRepository {
    @Override
    protected <S extends TerrainVision> UUID getIdFromEntity(S entity) {
        return entity.getTerrainVisionId();
    }
}
