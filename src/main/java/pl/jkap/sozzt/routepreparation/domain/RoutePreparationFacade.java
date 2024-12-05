package pl.jkap.sozzt.routepreparation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.filestorage.event.GeodeticMapUploadedEvent;
import pl.jkap.sozzt.routepreparation.dto.AddRoutePreparationDto;
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto;
import pl.jkap.sozzt.routepreparation.event.RoutePreparationCompletedEvent;
import pl.jkap.sozzt.routepreparation.exception.CompleteRoutePreparationException;
import pl.jkap.sozzt.routepreparation.exception.RoutePreparationNotFoundException;

import java.util.UUID;

@Slf4j
@Builder
public class RoutePreparationFacade {
    RoutePreparationRepository routePreparationRepository;
    RoutePreparationEventPublisher routePreparationEventPublisher;

    public void addRoutePreparation(AddRoutePreparationDto addRoutePreparationDto) {
        log.info("Adding route preparation with id {}", addRoutePreparationDto.getRoutePreparationId());
        routePreparationRepository.save(RoutePreparation.builder()
                .routePreparationId(addRoutePreparationDto.getRoutePreparationId())
                .deadline(addRoutePreparationDto.getDeadline())
                .build());
    }

    public RoutePreparationDto getRoutePreparation(UUID uuid) {
        return routePreparationRepository.findById(uuid)
                .map(RoutePreparation::dto)
                .orElseThrow(() -> new RoutePreparationNotFoundException(uuid));
    }

    public void completeRoutePreparation(UUID routePreparationId) {
        log.info("Completing route preparation with id {}", routePreparationId);
        RoutePreparation routePreparation = routePreparationRepository.findById(routePreparationId)
                .orElseThrow(() -> new RoutePreparationNotFoundException(routePreparationId));
        if(!routePreparation.isCompleted()) {
            throw new CompleteRoutePreparationException("Route preparation with id " + routePreparationId + " is not completed yet.");
        }
        routePreparationEventPublisher.routePreparationCompleted(new RoutePreparationCompletedEvent(routePreparationId));
    }

    private void markGeodeticMapUploaded(UUID routePreparationId) {
        log.info("Marking geodetic map uploaded for route preparation with id {}", routePreparationId);
        routePreparationRepository.findById(routePreparationId)
                .ifPresent(RoutePreparation::markGeodeticMapUploaded);
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onGeodeticMapUploadedEvent(GeodeticMapUploadedEvent event) {
        markGeodeticMapUploaded(event.getRoutePreparationId());
    }
}
