package pl.jkap.sozzt.terrainvision.domain;

import pl.jkap.sozzt.terrainvision.exception.CompletionTerrainVisionException;
import pl.jkap.sozzt.terrainvision.exception.InvalidProjectPurposesMapPreparationNeedException;

import java.time.Instant;
import java.util.UUID;

class InProgressTerrainVision extends TerrainVision {
    InProgressTerrainVision(UUID terrainVisionId, Instant deadline, ProjectPurposesMapPreparationNeed projectPurposesMapPreparationNeed) {
        super(terrainVisionId, false, deadline, TerrainVisionStatus.IN_PROGRESS, projectPurposesMapPreparationNeed);
    }

    void confirmAllPhotosAreUploaded() {
       allPhotosUploaded = true;
    }


    void setProjectPurposesMapPreparationNeed(ProjectPurposesMapPreparationNeed newProjectPurposesMapPreparationNeed) {
        if(ProjectPurposesMapPreparationNeed.NONE == newProjectPurposesMapPreparationNeed) {
            throw new InvalidProjectPurposesMapPreparationNeedException("Project purposes map preparation need has to be set");
        }
        this.projectPurposesMapPreparationNeed = newProjectPurposesMapPreparationNeed;
    }

    CompletedTerrainVision complete() {
        if(!allPhotosUploaded) {
            throw new CompletionTerrainVisionException("All photos must be uploaded");
        }
        if(ProjectPurposesMapPreparationNeed.NONE == projectPurposesMapPreparationNeed) {
            throw new InvalidProjectPurposesMapPreparationNeedException("Project purposes map preparation need has to be set");
        }
        return new CompletedTerrainVision(terrainVisionId, deadline, projectPurposesMapPreparationNeed);
    }
}
