package pl.jkap.sozzt.projectpurposesmappreparation.exception;

import java.util.UUID;

public class ProjectPurposesMapPreparationNotFoundException extends RuntimeException {

    public ProjectPurposesMapPreparationNotFoundException(UUID projectPurposesMapPreparationId) {
        super("Project purposes map preparation not found: " + projectPurposesMapPreparationId);
    }
}
