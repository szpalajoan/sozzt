package pl.jkap.sozzt.preliminaryplanning.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
@AllArgsConstructor
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

    public void confirmMapUploaded() {
        this.isPreliminaryMapUploaded = true;
    }
}
