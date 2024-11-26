package pl.jkap.sozzt.routepreparation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
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
}
