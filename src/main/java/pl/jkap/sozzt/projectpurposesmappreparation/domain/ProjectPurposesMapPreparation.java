package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
class ProjectPurposesMapPreparation {

    @Id
    private UUID projectPurposesMapPreparationId;
    Instant deadline;
    boolean isGeodeticMapUploaded;

    public ProjectPurposesMapPreparationDto dto() {
        return ProjectPurposesMapPreparationDto.builder()
                .projectPurposesMapPreparationId(projectPurposesMapPreparationId)
                .deadline(deadline)
                .isGeodeticMapUploaded(isGeodeticMapUploaded)
                .build();
    }

    void markGeodeticMapUploaded() {
        isGeodeticMapUploaded = true;
    }

    boolean isCompleted() {
        return isGeodeticMapUploaded;
    }
}
