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
}
