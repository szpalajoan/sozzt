package pl.jkap.sozzt.documentation.dto;

import lombok.*;

import java.time.Instant;
import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class AddDocumentationDto {
    private UUID contractId;
    private Instant deadline;
}
