package pl.jkap.sozzt.routedrawing.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RoutePreparationDto {
    private UUID routePreparationId;
    private Instant deadline;
    private MapVerificationDto mapVerification;
    private RouteDrawingDto routeDrawing;
}
