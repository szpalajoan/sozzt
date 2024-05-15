package pl.jkap.sozzt.preliminaryplanning.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class AddPreliminaryPlanDto {

    private UUID preliminaryPlanId;
    private Instant deadline;
}
