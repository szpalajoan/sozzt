package pl.jkap.sozzt.preliminaryplanning.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class PreliminaryPlanDto {

    private UUID preliminaryPlanId;
    private boolean isPreliminaryMapUploaded;
    private String googleMapUrl;
    private Instant deadline;
}
