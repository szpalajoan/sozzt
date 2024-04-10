package pl.jkap.sozzt.contract.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class LocationDto {
    private String region;
    private String district;
    private String city;
    private String transformerStationNumberWithCircuit;
    private String fieldNumber;
    private String googleMapLink;
}
