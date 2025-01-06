package pl.jkap.sozzt.documentation.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TermVerificationDto {

    private String verifierName;
    private boolean areAllTermsActual;
    private boolean areContractsCorrect;
}
