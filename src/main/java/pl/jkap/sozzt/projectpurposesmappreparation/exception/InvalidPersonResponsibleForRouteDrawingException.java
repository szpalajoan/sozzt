package pl.jkap.sozzt.projectpurposesmappreparation.exception;

import org.springframework.http.HttpStatus;

public class InvalidPersonResponsibleForRouteDrawingException extends ProjectPurposesMapPreparationException {
    public InvalidPersonResponsibleForRouteDrawingException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "invalid.person.responsible.for.route.drawing.error");
    }
}
