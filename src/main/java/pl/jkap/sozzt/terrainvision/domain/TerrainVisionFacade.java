package pl.jkap.sozzt.terrainvision.domain;

import lombok.Builder;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.terrainvision.dto.AddTerrainVisionDto;
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionNotFoundException;

import java.util.UUID;

@Builder
public class TerrainVisionFacade {

    private final TerrainVisionRepository terrainVisionRepository;
    private final InstantProvider instantProvider;


    public TerrainVisionDto getTerrainVision(UUID terrainVisionId) {
        TerrainVision terrainVision = terrainVisionRepository.findById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        return terrainVision.dto();
    }

    public void addTerrainVision(AddTerrainVisionDto addTerrainVisionDto) {
        TerrainVision terrainVision = TerrainVision.builder()
                .terrainVisionId(addTerrainVisionDto.getTerrainVisionId())
                .deadline(addTerrainVisionDto.getDeadLine())
                .build();
        terrainVisionRepository.save(terrainVision);
    }

    public void beginTerrainVision(UUID contractId) {

    }
}
