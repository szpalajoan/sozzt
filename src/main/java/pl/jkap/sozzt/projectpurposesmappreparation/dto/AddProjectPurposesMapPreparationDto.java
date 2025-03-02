package pl.jkap.sozzt.projectpurposesmappreparation.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddProjectPurposesMapPreparationDto {
    UUID projectPurposesMapPreparationId;
    Instant deadline;
}
