package pl.jkap.sozzt.terrainvision.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class TerrainVisionDto {

    private UUID terrainVisionId;

}
