package pl.jkap.sozzt.projectpurposesmappreparation.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.config.SozztException;

public abstract class ProjectPurposesMapPreparationException extends SozztException {
    public ProjectPurposesMapPreparationException(String message, HttpStatus status, String codeError) {
        super(message, status, codeError);
    }
}
