package pl.jkap.sozzt.routepreparation.exception;

import org.springframework.http.HttpStatus;
import pl.jkap.sozzt.projectpurposesmappreparation.exception.ProjectPurposesMapPreparationException;

import java.util.UUID;

public class RoutePreparationNotFoundException extends ProjectPurposesMapPreparationException {

    public RoutePreparationNotFoundException(UUID id) {
        super("Route preparation not found: " + id, HttpStatus.NOT_FOUND, "routepreparation.not-found.error");
    }
}
