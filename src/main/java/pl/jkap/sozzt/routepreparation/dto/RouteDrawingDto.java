package pl.jkap.sozzt.routepreparation.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RouteDrawingDto {
    private String drawingBy;
    private UUID mapWithRouteFileId;
    private UUID routeWithDataFileId;
}
