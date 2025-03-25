package pl.jkap.sozzt.landextracts.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddLandExtractsDto {
    private UUID landExtractsId;
    private Instant deadline;
} 