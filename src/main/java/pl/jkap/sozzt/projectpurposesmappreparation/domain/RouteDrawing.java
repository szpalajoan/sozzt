package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import lombok.Getter;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.RouteDrawingDto;

import java.util.UUID;

@Getter
class RouteDrawing {
    private final String drawingBy;
    private final UUID mapWithRouteFileId;
    private final UUID routeWithDataFileId;

    private RouteDrawing(String drawingBy, UUID mapWithRouteFileId, UUID routeWithDataFileId) {
        this.drawingBy = drawingBy;
        this.mapWithRouteFileId = mapWithRouteFileId;
        this.routeWithDataFileId = routeWithDataFileId;
    }

    static RouteDrawing notStartedRouteDrawing() {
        return new RouteDrawing(null, null, null);
    }

    RouteDrawing startRouteDrawing(String drawingBy) {
        return new RouteDrawing(drawingBy, null, null);
    }

    RouteDrawing withDrawnRoute(UUID mapWithRouteFileId) {
        return new RouteDrawing(drawingBy, mapWithRouteFileId, routeWithDataFileId);
    }

    RouteDrawing withPdfWithRouteAndData(UUID pdfWithRouteAndDatafileId) {
        return new RouteDrawing(drawingBy, mapWithRouteFileId, pdfWithRouteAndDatafileId);
    }

    boolean isCompleted() {
        return mapWithRouteFileId != null && routeWithDataFileId != null;
    }

    RouteDrawingDto dto() {
        return RouteDrawingDto.builder()
                .drawingBy(drawingBy)
                .mapWithRouteFileId(mapWithRouteFileId)
                .routeWithDataFileId(routeWithDataFileId)
                .build();
    }
} 