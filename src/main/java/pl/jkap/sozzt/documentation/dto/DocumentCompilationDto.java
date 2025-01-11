package pl.jkap.sozzt.documentation.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class DocumentCompilationDto {
    private UUID compiledDocumentId;
}
