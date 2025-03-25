package pl.jkap.sozzt.landextracts.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LandExtractsDto {
    UUID landExtractsId;
    boolean requestForPlotExtractsSent;
    boolean completed;
} 