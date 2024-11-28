package pl.jkap.sozzt.routepreparation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.filestorage.event.GeodeticMapUploadedEvent;
import pl.jkap.sozzt.routepreparation.dto.AddRoutePreparationDto;
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto;
import pl.jkap.sozzt.routepreparation.exception.RoutePreparationNotFoundException;

import java.util.UUID;

@Slf4j
@Builder
public class RoutePreparationFacade {
    RoutePreparationRepository routePreparationRepository;

    public void addRoutePreparation(AddRoutePreparationDto addRoutePreparationDto) {
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

    private void markGeodeticMapUploaded(UUID routePreparationId) {
        routePreparationRepository.findById(routePreparationId)
                .ifPresent(RoutePreparation::markGeodeticMapUploaded);
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onGeodeticMapUploadedEvent(GeodeticMapUploadedEvent event) {
        markGeodeticMapUploaded(event.getRoutePreparationId());
    }
}
