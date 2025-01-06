package pl.jkap.sozzt.documentation.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.documentation.dto.DocumentationDto;
import pl.jkap.sozzt.instant.InstantProvider;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
class Documentation {
    private UUID documentationId;
    private Instant deadline;
    private MapVerification mapVerification;
    private RouteDrawing routeDrawing;
    private ConsentsVerification consentsVerification;
    private DocumentCompilation documentCompilation;
    private TauronCommunication tauronCommunication;
    private TermVerification termVerification;


    void approveCorrectnessOfTheMap(InstantProvider instantProvider) {
        mapVerification.approveCorrectnessOfTheMap(instantProvider);
    }

    void startRouteDrawing(String user) {
        routeDrawing = new RouteDrawing(user);
    }

    void addDrawnRoute(UUID mapWithRouteFileId) {
        routeDrawing = routeDrawing.withDrawnRoute(mapWithRouteFileId);
    }

    void addPdfWithRouteAndData(UUID pdfWithRouteAndDatafileId) {
        routeDrawing = routeDrawing.withPdfWithRouteAndData(pdfWithRouteAndDatafileId);
        startConsentsVerification();
    }

    private void startConsentsVerification() {
        consentsVerification = new ConsentsVerification();
    }

    void completeConsentsVerification() {
        consentsVerification = consentsVerification.completedConsentsVerification();
        startDocumentCompilation();
    }

    private void startDocumentCompilation() {
        documentCompilation = new DocumentCompilation();
    }

    void completeDocumentCompilation(UUID compiledDocumentId) {
        documentCompilation = documentCompilation.withCompiledDocument(compiledDocumentId);
        startTauronCommunication();
    }

    private void startTauronCommunication() {
        tauronCommunication = new TauronCommunication();
    }

    void markPrintedDocumentationSentToTauronAsDone() {
        tauronCommunication =tauronCommunication.markPrintedDocumentationSentToTauronAsDone();
    }

    void addTauronApprove() {
        tauronCommunication = tauronCommunication.markApprovedByTauron();
    }

    void startTermVerification(String verifierName) {
        termVerification = new TermVerification(verifierName);
    }

    void approveThatAllTermsAreActual() {
        termVerification = termVerification.approveThatAllTermsAreActual();
    }

    void complete() {
        termVerification = termVerification.approveThatContractsAreCorrect();
    }

    DocumentationDto dto() {
        return DocumentationDto.builder()
                .documentationId(documentationId)
                .deadline(deadline)
                .correctnessOfTheMap(mapVerification.isCorrectnessOfTheMap())
                .routeDrawing(routeDrawing != null ? routeDrawing.dto() : null)
                .consentsVerification(consentsVerification != null ? consentsVerification.dto() : null)
                .documentCompilation(documentCompilation != null ? documentCompilation.dto() : null)
                .tauronCommunication(tauronCommunication != null ? tauronCommunication.dto() : null)
                .termVerification(termVerification != null ? termVerification.dto() : null)
                .build();
    }

}
