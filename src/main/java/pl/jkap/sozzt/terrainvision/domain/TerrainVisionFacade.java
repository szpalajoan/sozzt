package pl.jkap.sozzt.terrainvision.domain;

import lombok.Builder;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.terrainvision.dto.AddTerrainVisionDto;
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;
import pl.jkap.sozzt.terrainvision.event.TerrainVisionCompletedEvent;
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionAccessException;
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionNotFoundException;

import java.util.UUID;

@Builder
public class TerrainVisionFacade {

    private final TerrainVisionRepository terrainVisionRepository;
    private final InstantProvider instantProvider;
    private final TerrainVisionEventPublisher terrainVisionEventPublisher;


    public TerrainVisionDto getTerrainVision(UUID terrainVisionId) {
        TerrainVision terrainVision = terrainVisionRepository.findById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        return terrainVision.dto();
    }

    public void addTerrainVision(AddTerrainVisionDto addTerrainVisionDto) {
        HoldTerrainVision terrainVision = new HoldTerrainVision(addTerrainVisionDto.getTerrainVisionId(), addTerrainVisionDto.getDeadLine());
        terrainVisionRepository.save(terrainVision);
    }

    public void beginTerrainVision(UUID terrainVisionId) {
        HoldTerrainVision terrainVision = terrainVisionRepository.findHoldTerrainVisionById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        InProgressTerrainVision inProgressTerrainVision = terrainVision.begin();
        terrainVisionRepository.save(inProgressTerrainVision);
    }

    public void confirmAllPhotosAreUploaded(UUID terrainVisionId) {
        checkCanModifyTerrainVision();
        InProgressTerrainVision inProgressTerrainVision = terrainVisionRepository.findInProgressTerrainVisionById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        inProgressTerrainVision.confirmAllPhotosAreUploaded();
        terrainVisionRepository.save(inProgressTerrainVision);

    }

    public void confirmChangesOnMap(UUID terrainVisionId, TerrainVisionDto.MapChange mapChange) {
        checkCanModifyTerrainVision();
        InProgressTerrainVision inProgressTerrainVision = terrainVisionRepository.findInProgressTerrainVisionById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        inProgressTerrainVision.confirmChangesOnMap(mapChange);
        terrainVisionRepository.save(inProgressTerrainVision);

    }

    public void completeTerrainVision(UUID terrainVisionId) {
        checkCanModifyTerrainVision();
        InProgressTerrainVision inProgressTerrainVision = terrainVisionRepository.findInProgressTerrainVisionById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        CompletedTerrainVision completedTerrainVision = inProgressTerrainVision.complete();
        terrainVisionRepository.save(completedTerrainVision);
        terrainVisionEventPublisher.terrainVisionCompleted(new TerrainVisionCompletedEvent(terrainVisionId,
                        completedTerrainVision.getMapChange().equals(TerrainVision.MapChange.MODIFIED)));
    }

    private void checkCanModifyTerrainVision() {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("TERRAIN_VISIONER"))) {
            throw new TerrainVisionAccessException("You are not authorized to modify terrain vision");
        }
    }


}
