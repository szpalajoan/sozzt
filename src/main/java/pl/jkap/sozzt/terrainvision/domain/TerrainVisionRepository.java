package pl.jkap.sozzt.terrainvision.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;

interface TerrainVisionRepository extends CrudRepository<TerrainVision, UUID> {
}
