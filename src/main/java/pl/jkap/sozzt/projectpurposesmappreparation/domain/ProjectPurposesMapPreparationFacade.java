package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.filestorage.event.GeodeticMapUploadedEvent;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.AddProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.projectpurposesmappreparation.event.ProjectPurposesMapPreparationCompletedEvent;
import pl.jkap.sozzt.projectpurposesmappreparation.exception.CompleteProjectPurposesMapPreparationException;
import pl.jkap.sozzt.projectpurposesmappreparation.exception.ProjectPurposesMapPreparationNotFoundException;

import java.util.UUID;

@Slf4j
@Builder
public class ProjectPurposesMapPreparationFacade {
    ProjectPurposesMapPreparationRepository projectPurposesMapPreparationRepository;
    ProjectPurposesMapPreparationEventPublisher projectPurposesMapPreparationEventPublisher;

    public void addProjectPurposesMapPreparation(AddProjectPurposesMapPreparationDto addProjectPurposesMapPreparationDto) {
        log.info("Adding project purposes map preparation with id {}", addProjectPurposesMapPreparationDto.getProjectPurposesMapPreparationId());
        projectPurposesMapPreparationRepository.save(ProjectPurposesMapPreparation.builder()
                .projectPurposesMapPreparationId(addProjectPurposesMapPreparationDto.getProjectPurposesMapPreparationId())
                .deadline(addProjectPurposesMapPreparationDto.getDeadline())
                .build());
    }

    public ProjectPurposesMapPreparationDto getProjectPurposesMapPreparation(UUID uuid) {
        return projectPurposesMapPreparationRepository.findById(uuid)
                .map(ProjectPurposesMapPreparation::dto)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(uuid));
    }

    public void completeProjectPurposesMapPreparation(UUID projectPurposesMapPreparationId) {
        log.info("Completing project purposes map preparation with id {}", projectPurposesMapPreparationId);
        ProjectPurposesMapPreparation projectPurposesMapPreparation = projectPurposesMapPreparationRepository.findById(projectPurposesMapPreparationId)
                .orElseThrow(() -> new ProjectPurposesMapPreparationNotFoundException(projectPurposesMapPreparationId));
        if(!projectPurposesMapPreparation.isCompleted()) {
            throw new CompleteProjectPurposesMapPreparationException("ProjectPurposesMapPreparation is not completed: " + projectPurposesMapPreparationId);
        }
        projectPurposesMapPreparationEventPublisher.projectPurposesMapPreparationCompleted(new ProjectPurposesMapPreparationCompletedEvent(projectPurposesMapPreparationId));
    }

    private void markGeodeticMapUploaded(UUID projectPurposesMapPreparationId) {
        log.info("Marking geodetic map uploaded for project purposes map preparation with id {}", projectPurposesMapPreparationId);
        projectPurposesMapPreparationRepository.findById(projectPurposesMapPreparationId)
                .ifPresent(ProjectPurposesMapPreparation::markGeodeticMapUploaded);
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onGeodeticMapUploadedEvent(GeodeticMapUploadedEvent event) {
        markGeodeticMapUploaded(event.getProjectPurposesMapPreparationId());
    }
}
