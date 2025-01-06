package pl.jkap.sozzt.documentation.domain;

import lombok.Getter;
import pl.jkap.sozzt.documentation.dto.RouteDrawingDto;

import java.util.UUID;


@Getter
class RouteDrawing {
    final private String drawingBy;
    final private UUID mapWithRouteFileId;
    final private UUID routeWithDataFileId;

    RouteDrawing(String drawingBy) {
        this(drawingBy, null, null);
    }

    private RouteDrawing(String drawingBy, UUID mapWithRouteFileId, UUID routeWithDataFileId) {
        this.drawingBy = drawingBy;
        this.mapWithRouteFileId = mapWithRouteFileId;
        this.routeWithDataFileId = routeWithDataFileId;
    }

    RouteDrawing withDrawnRoute(UUID mapWithRouteFileId) {
        return new RouteDrawing(drawingBy, mapWithRouteFileId, routeWithDataFileId);
    }

    RouteDrawing withPdfWithRouteAndData(UUID pdfWithRouteAndDatafileId) {
        return new RouteDrawing(drawingBy, mapWithRouteFileId, pdfWithRouteAndDatafileId);
    }

    RouteDrawingDto dto() {
        return RouteDrawingDto.builder()
                .drawingBy(drawingBy)
                .mapWithRouteFileId(mapWithRouteFileId)
                .routeWithDataFileId(routeWithDataFileId)
                .build();
    }

}
