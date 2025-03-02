package pl.jkap.sozzt.projectpurposesmappreparation.exception;

import org.springframework.http.HttpStatus;

import java.util.UUID;

public class ProjectPurposesMapPreparationNotFoundException extends ProjectPurposesMapPreparationException {

    public ProjectPurposesMapPreparationNotFoundException(UUID id) {
        super("Project purposes map preparation not found: " + id, HttpStatus.NOT_FOUND, "projectpurposesmappreparation.not-found.error");
    }
}
