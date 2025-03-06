package pl.jkap.sozzt.routedrawing.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RoutePreparationDto {
    private MapVerificationDto mapVerification;
    private RouteDrawingDto routeDrawing;
}
