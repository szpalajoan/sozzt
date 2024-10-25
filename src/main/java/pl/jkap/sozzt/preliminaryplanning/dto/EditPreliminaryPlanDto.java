package pl.jkap.sozzt.preliminaryplanning.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class EditPreliminaryPlanDto {
    private String googleMapUrl;
}
