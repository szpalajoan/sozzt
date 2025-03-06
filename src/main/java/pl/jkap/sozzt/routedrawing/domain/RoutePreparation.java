package pl.jkap.sozzt.routedrawing.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.routedrawing.dto.RoutePreparationDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
class RoutePreparation {

    @Id
    private UUID routePreparationId;
    private MapVerification mapVerification;
    private RouteDrawing routeDrawing;


    void approveCorrectnessOfTheMap(InstantProvider instantProvider) {
        mapVerification.approveCorrectnessOfTheMap(instantProvider);
    }

    void startRouteDrawing(String user) {
        routeDrawing = routeDrawing.startRouteDrawing(user);
    }

    void addDrawnRoute(UUID mapWithRouteFileId) {
        routeDrawing = routeDrawing.withDrawnRoute(mapWithRouteFileId);
    }

    void addPdfWithRouteAndData(UUID pdfWithRouteAndDatafileId) {
        routeDrawing = routeDrawing.withPdfWithRouteAndData(pdfWithRouteAndDatafileId);
    }

    boolean isCompleted() {
        return mapVerification != null
            && mapVerification.isCompleted()
            && routeDrawing != null 
            && routeDrawing.isCompleted();
    }

    RoutePreparationDto dto() {
        return RoutePreparationDto.builder()
                .mapVerification(mapVerification.dto())
                .routeDrawing(routeDrawing.dto())
                .build();
    }
}
