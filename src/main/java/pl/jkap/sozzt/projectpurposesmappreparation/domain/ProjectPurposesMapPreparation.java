package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.instant.InstantProvider;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
class ProjectPurposesMapPreparation {

    @Id
    private UUID projectPurposesMapPreparationId;
    private Instant deadline;
    private boolean isGeodeticMapUploaded;
    private MapVerification mapVerification;
    private RouteDrawing routeDrawing;

    void markGeodeticMapUploaded() {
        isGeodeticMapUploaded = true;
        mapVerification = new MapVerification();
    }

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
        return isGeodeticMapUploaded 
            && mapVerification != null 
            && mapVerification.isCompleted()
            && routeDrawing != null 
            && routeDrawing.isCompleted();
    }

    ProjectPurposesMapPreparationDto dto() {
        return ProjectPurposesMapPreparationDto.builder()
                .projectPurposesMapPreparationId(projectPurposesMapPreparationId)
                .deadline(deadline)
                .isGeodeticMapUploaded(isGeodeticMapUploaded)
                .correctnessOfTheMap(mapVerification != null ? mapVerification.isCorrectnessOfTheMap() : false)
                .routeDrawing(routeDrawing.dto())
                .build();
    }
}
