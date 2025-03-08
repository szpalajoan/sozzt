package pl.jkap.sozzt.routedrawing.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddRoutePreparationDto {
    UUID routePreparationId;
    Instant deadline;
}
