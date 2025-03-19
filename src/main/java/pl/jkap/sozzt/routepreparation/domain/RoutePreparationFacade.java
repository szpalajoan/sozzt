package pl.jkap.sozzt.routepreparation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.routepreparation.dto.AddRoutePreparationDto;
import pl.jkap.sozzt.routepreparation.event.RoutePreparationCompletedEvent;
import pl.jkap.sozzt.routepreparation.exception.CompleteRoutePreparationException;
import pl.jkap.sozzt.routepreparation.exception.InvalidPersonResponsibleForRouteDrawingException;
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto;
import pl.jkap.sozzt.routepreparation.exception.RoutePreparationNotFoundException;

import java.util.UUID;

@Slf4j
@Builder
public class RoutePreparationFacade {

    RoutePreparationRepository routePreparationRepository;
    RoutePreparationEventPublisher routePreparationEventPublisher;

    FileStorageFacade fileStorageFacade;
    InstantProvider instantProvider;

    public void addRoutePreparation(AddRoutePreparationDto addRoutePreparationDto) {
        log.info("Adding route preparation with id {}", addRoutePreparationDto.getRoutePreparationId());
        routePreparationRepository.save(new RoutePreparation(addRoutePreparationDto));
    }

    public RoutePreparationDto getRoutePreparation(UUID id) {
        return routePreparationRepository.findById(id)
                .map(RoutePreparation::dto)
                .orElseThrow(() -> new RoutePreparationNotFoundException(id));
    }

    public void approveCorrectnessOfTheMap(UUID id) {
        RoutePreparation routePreparation = getPreparation(id);
        routePreparation.approveCorrectnessOfTheMap(instantProvider);
        routePreparationRepository.save(routePreparation);
    }

    public void choosePersonResponsibleForRouteDrawing(UUID id, String user) {
        if (user == null) {
            throw new InvalidPersonResponsibleForRouteDrawingException("Person responsible for route drawing cannot be null");
        }
        RoutePreparation routePreparation = getPreparation(id);
        routePreparation.startRouteDrawing(user);
        routePreparationRepository.save(routePreparation);
    }

    public FileDto uploadDrawnRoute(UUID id, AddFileDto addFileDto) {
        RoutePreparation routePreparation = getPreparation(id);
        FileDto fileDto = fileStorageFacade.addMapWithRoute(addFileDto);
        routePreparation.addDrawnRoute(fileDto.getFileId());
        routePreparationRepository.save(routePreparation);
        return fileDto;
    }

    public FileDto uploadPdfWithRouteAndData(UUID id, AddFileDto addFileDto) {
        RoutePreparation routePreparation = getPreparation(id);
        FileDto fileDto = fileStorageFacade.addPdfWithRouteAndData(addFileDto);
        routePreparation.addPdfWithRouteAndData(fileDto.getFileId());
        routePreparationRepository.save(routePreparation);
        return fileDto;
    }

    public RoutePreparationDto completeRoutePreparation(UUID id) {
        log.info("Completing route preparation with id {}", id);
        RoutePreparation routePreparation = getPreparation(id);
        if(!routePreparation.isCompleted()) {
            throw new CompleteRoutePreparationException(id);
        }
        routePreparationEventPublisher.routePreparationCompleted(new RoutePreparationCompletedEvent(id));
        return routePreparation.dto();
    }


    private RoutePreparation getPreparation(UUID id) {
        return routePreparationRepository.findById(id)
                .orElseThrow(() -> new RoutePreparationNotFoundException(id));
    }

}
