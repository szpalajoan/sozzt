package pl.jkap.sozzt.terrainvision.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ConfirmChangesOnMapDto {
    private TerrainVisionDto.MapChange mapChange;
}
