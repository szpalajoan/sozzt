package pl.jkap.sozzt.preliminaryplanning.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Builder
@Getter
class PreliminaryPlan {

    @Id
    private UUID preliminaryPlanId;
    private Instant deadline;

    public PreliminaryPlanDto dto() {
        return PreliminaryPlanDto.builder()
                .preliminaryPlanId(preliminaryPlanId)
                .deadline(deadline)
                .build();
    }
}
