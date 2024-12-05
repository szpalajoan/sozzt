package pl.jkap.sozzt.terrainvision.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class ConfirmChangesOnMapDto {
    private TerrainVisionDto.MapChange mapChange;
}
