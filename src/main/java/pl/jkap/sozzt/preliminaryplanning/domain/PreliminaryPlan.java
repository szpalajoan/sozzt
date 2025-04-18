package pl.jkap.sozzt.preliminaryplanning.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.ToString;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
@ToString
class PreliminaryPlan {

    @Id
    private UUID preliminaryPlanId;
    private boolean isPreliminaryMapUploaded;
    private String googleMapUrl;
    private Instant deadline;

    public PreliminaryPlan(UUID preliminaryPlanId, Instant deadline) {
        this.preliminaryPlanId = preliminaryPlanId;
        this.deadline = deadline;
    }

    public PreliminaryPlanDto dto() {
        return PreliminaryPlanDto.builder()
                .preliminaryPlanId(preliminaryPlanId)
                .isPreliminaryMapUploaded(isPreliminaryMapUploaded)
                .googleMapUrl(googleMapUrl)
                .deadline(deadline)
                .build();
    }

    void confirmMapAdded() {
        this.isPreliminaryMapUploaded = true;
    }

    void confirmMapDeleted() {
        this.isPreliminaryMapUploaded = false;
    }

    void addGoogleMapUrl(String googleMapUrl) {
        this.googleMapUrl = googleMapUrl;
    }

    boolean isCompleted() {
        return isPreliminaryMapUploaded && googleMapUrl != null;
    }
}
