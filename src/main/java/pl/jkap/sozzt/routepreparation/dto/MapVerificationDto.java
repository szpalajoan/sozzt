package pl.jkap.sozzt.routepreparation.dto;

import lombok.*;

import java.time.Instant;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class MapVerificationDto {
    private boolean correctnessOfTheMap;
    private Instant verificationDate;
}
