package pl.jkap.sozzt.routepreparation.exception;

import java.util.UUID;

public class CompleteRoutePreparationException extends RuntimeException {

    public CompleteRoutePreparationException(UUID id) {
        super("Route preparation is not completed: " + id);
    }
}
