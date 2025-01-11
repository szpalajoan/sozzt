package pl.jkap.sozzt.documentation.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class TauronCommunicationDto {
    boolean isPrintedDocumentationSentToTauron;
    boolean isApprovedByTauron;
}
