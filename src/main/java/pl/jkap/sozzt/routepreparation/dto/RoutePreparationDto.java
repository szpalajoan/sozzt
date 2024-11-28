package pl.jkap.sozzt.routepreparation.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class RoutePreparationDto {

    private UUID routePreparationId;
    private Instant deadline;
    private boolean isGeodeticMapUploaded;
}
