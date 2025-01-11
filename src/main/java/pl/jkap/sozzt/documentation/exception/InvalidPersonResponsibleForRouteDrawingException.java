package pl.jkap.sozzt.documentation.exception;

import org.springframework.http.HttpStatus;

public class InvalidPersonResponsibleForRouteDrawingException extends DocumentationException {
    public InvalidPersonResponsibleForRouteDrawingException(String message) {
        super(message, HttpStatus.BAD_REQUEST, "invalid.person.responsible.for.route.drawing.error");
    }
}
