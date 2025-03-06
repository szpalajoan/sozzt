package pl.jkap.sozzt.routedrawing.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

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
