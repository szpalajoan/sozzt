package pl.jkap.sozzt.routedrawing.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.projectpurposesmappreparation.exception.ProjectPurposesMapPreparationException;

public class InvalidPersonResponsibleForRouteDrawingException extends ProjectPurposesMapPreparationException {
    public InvalidPersonResponsibleForRouteDrawingException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "invalid.person.responsible.for.route.drawing.error");
    }
}
