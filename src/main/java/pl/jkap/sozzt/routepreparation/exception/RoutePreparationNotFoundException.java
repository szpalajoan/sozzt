package pl.jkap.sozzt.routepreparation.exception;

import java.util.UUID;

public class RoutePreparationNotFoundException extends RuntimeException {

    public RoutePreparationNotFoundException(UUID routePreparationId) {
        super("Route preparation with id " + routePreparationId + " not found.");
    }
}
