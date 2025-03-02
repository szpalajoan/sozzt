package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.AddProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.projectpurposesmappreparation.event.ProjectPurposesMapPreparationCompletedEvent;
import pl.jkap.sozzt.projectpurposesmappreparation.exception.CompleteProjectPurposesMapPreparationException;
import pl.jkap.sozzt.projectpurposesmappreparation.exception.ProjectPurposesMapPreparationNotFoundException;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.projectpurposesmappreparation.exception.InvalidPersonResponsibleForRouteDrawingException;

import java.util.UUID;

@Slf4j
@Builder
public class ProjectPurposesMapPreparationFacade {
    ProjectPurposesMapPreparationRepository projectPurposesMapPreparationRepository;
    ProjectPurposesMapPreparationEventPublisher projectPurposesMapPreparationEventPublisher;
    FileStorageFacade fileStorageFacade;
    InstantProvider instantProvider;


    public void addProjectPurposesMapPreparation(AddProjectPurposesMapPreparationDto addProjectPurposesMapPreparationDto) {
        log.info("Adding project purposes map preparation with id {}", addProjectPurposesMapPreparationDto.getProjectPurposesMapPreparationId());
        projectPurposesMapPreparationRepository.save(ProjectPurposesMapPreparation.builder()
                                                            .projectPurposesMapPreparationId(addProjectPurposesMapPreparationDto.getProjectPurposesMapPreparationId())
                                                            .deadline(addProjectPurposesMapPreparationDto.getDeadline())
                                                            .routeDrawing(RouteDrawing.notStartedRouteDrawing())
                                                            .build());
    }

    public ProjectPurposesMapPreparationDto getProjectPurposesMapPreparation(UUID uuid) {
        return projectPurposesMapPreparationRepository.findById(uuid)
                .map(ProjectPurposesMapPreparation::dto)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(uuid));
    }

    public ProjectPurposesMapPreparationDto completeProjectPurposesMapPreparation(UUID projectPurposesMapPreparationId) {
        log.info("Completing project purposes map preparation with id {}", projectPurposesMapPreparationId);
        ProjectPurposesMapPreparation projectPurposesMapPreparation = projectPurposesMapPreparationRepository.findById(projectPurposesMapPreparationId)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(projectPurposesMapPreparationId));
        if(!projectPurposesMapPreparation.isCompleted()) {
            throw new CompleteProjectPurposesMapPreparationException("ProjectPurposesMapPreparation is not completed: " + projectPurposesMapPreparationId);
        }
        projectPurposesMapPreparationEventPublisher.projectPurposesMapPreparationCompleted(new ProjectPurposesMapPreparationCompletedEvent(projectPurposesMapPreparationId));
        return projectPurposesMapPreparation.dto();
    }



    public FileDto addGeodeticMap(AddFileDto addFileDto) {
        log.info("Adding geodetic map for project purposes map preparation with id {}", addFileDto.getContractId());
        FileDto fileDto = fileStorageFacade.addGeodeticMap(addFileDto);
        projectPurposesMapPreparationRepository.findById(addFileDto.getContractId())
                .ifPresent(ProjectPurposesMapPreparation::markGeodeticMapUploaded);
        return fileDto;
    }


    public void approveCorrectnessOfTheMap(UUID id) {
        ProjectPurposesMapPreparation preparation = projectPurposesMapPreparationRepository.findById(id)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(id));
        preparation.approveCorrectnessOfTheMap(instantProvider);
        projectPurposesMapPreparationRepository.save(preparation);
    }

    public void choosePersonResponsibleForRouteDrawing(UUID id, String user) {
        if (user == null) {
            throw new InvalidPersonResponsibleForRouteDrawingException("Person responsible for route drawing cannot be null");
        }
        ProjectPurposesMapPreparation preparation = projectPurposesMapPreparationRepository.findById(id)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(id));
        preparation.startRouteDrawing(user);
        projectPurposesMapPreparationRepository.save(preparation);
    }

    public FileDto uploadDrawnRoute(UUID id, AddFileDto addFileDto) {
        ProjectPurposesMapPreparation preparation = projectPurposesMapPreparationRepository.findById(id)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(id));
        FileDto fileDto = fileStorageFacade.addMapWithRoute(addFileDto);
        preparation.addDrawnRoute(fileDto.getFileId());
        projectPurposesMapPreparationRepository.save(preparation);
        return fileDto;
    }

    public FileDto uploadPdfWithRouteAndData(UUID id, AddFileDto addFileDto) {
        ProjectPurposesMapPreparation preparation = projectPurposesMapPreparationRepository.findById(id)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(id));
        FileDto fileDto = fileStorageFacade.addPdfWithRouteAndData(addFileDto);
        preparation.addPdfWithRouteAndData(fileDto.getFileId());
        projectPurposesMapPreparationRepository.save(preparation);
        return fileDto;
    }
}
