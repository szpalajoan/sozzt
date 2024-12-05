package pl.jkap.sozzt.routepreparation.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto;

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
    Instant deadline;
    boolean isGeodeticMapUploaded;

    public RoutePreparationDto dto() {
        return RoutePreparationDto.builder()
                .routePreparationId(routePreparationId)
                .deadline(deadline)
                .isGeodeticMapUploaded(isGeodeticMapUploaded)
                .build();
    }

    void markGeodeticMapUploaded() {
        isGeodeticMapUploaded = true;
    }

    boolean isCompleted() {
        return isGeodeticMapUploaded;
    }
}
