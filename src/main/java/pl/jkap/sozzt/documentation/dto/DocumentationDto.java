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
public class DocumentationDto {

    private UUID documentationId;
    private Instant deadline;
    private boolean correctnessOfTheMap;
    private RouteDrawingDto routeDrawing;
    private ConsentsVerificationDto consentsVerification;
    private DocumentCompilationDto documentCompilation;
    private TauronCommunicationDto tauronCommunication;
    private TermVerificationDto termVerification;
}
