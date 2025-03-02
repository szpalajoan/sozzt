package pl.jkap.sozzt.terrainvision.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.terrainvision.dto.AddTerrainVisionDto;
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;
import pl.jkap.sozzt.terrainvision.event.TerrainVisionCompletedEvent;
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionAccessException;
import pl.jkap.sozzt.terrainvision.exception.TerrainVisionNotFoundException;

import java.util.UUID;

import static pl.jkap.sozzt.terrainvision.domain.ProjectPurposesMapPreparationNeed.NECESSARY;

@Builder
@Slf4j
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
        log.info("Confirming all photos are uploaded: {}", terrainVisionId);
        checkCanModifyTerrainVision();
        InProgressTerrainVision inProgressTerrainVision = terrainVisionRepository.findInProgressTerrainVisionById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        inProgressTerrainVision.confirmAllPhotosAreUploaded();
        terrainVisionRepository.save(inProgressTerrainVision);

    }

    public void setProjectPurposesMapPreparationNeed(UUID terrainVisionId, ProjectPurposesMapPreparationNeed projectPurposesMapPreparationNeed) {
        log.info("Setting project purposes map preparation need: {}", terrainVisionId);
        checkCanModifyTerrainVision();
        InProgressTerrainVision inProgressTerrainVision = terrainVisionRepository.findInProgressTerrainVisionById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        inProgressTerrainVision.setProjectPurposesMapPreparationNeed(projectPurposesMapPreparationNeed);
        terrainVisionRepository.save(inProgressTerrainVision);
    }

    public void completeTerrainVision(UUID terrainVisionId) {
        checkCanModifyTerrainVision();
        InProgressTerrainVision inProgressTerrainVision = terrainVisionRepository.findInProgressTerrainVisionById(terrainVisionId)
                .orElseThrow(() -> new TerrainVisionNotFoundException("TerrainVision not found: " + terrainVisionId));
        CompletedTerrainVision completedTerrainVision = inProgressTerrainVision.complete();
        terrainVisionRepository.save(completedTerrainVision);
        terrainVisionEventPublisher.terrainVisionCompleted(new TerrainVisionCompletedEvent(terrainVisionId,
                        completedTerrainVision.getProjectPurposesMapPreparationNeed().equals(NECESSARY)));
    }

    private void checkCanModifyTerrainVision() {
        if (SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_TERRAIN_VISIONER"))) {
            throw new TerrainVisionAccessException("You are not authorized to modify terrain vision");
        }
    }


}
