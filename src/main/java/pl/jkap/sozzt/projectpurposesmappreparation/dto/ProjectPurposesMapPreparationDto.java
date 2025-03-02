package pl.jkap.sozzt.projectpurposesmappreparation.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ProjectPurposesMapPreparationDto {

    private UUID projectPurposesMapPreparationId;
    private Instant deadline;
    private boolean isGeodeticMapUploaded;
    private boolean correctnessOfTheMap;
    private RouteDrawingDto routeDrawing;
}
