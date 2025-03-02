package pl.jkap.sozzt.documentation.domain;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.documentation.dto.DocumentationDto;

import java.time.Instant;
import java.util.UUID;

@Entity
@Getter
@Builder
@AllArgsConstructor
class Documentation {
    private UUID documentationId;
    private Instant deadline;
    private ConsentsVerification consentsVerification;
    private DocumentCompilation documentCompilation;
    private TauronCommunication tauronCommunication;
    private TermVerification termVerification;

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
        tauronCommunication = tauronCommunication.markPrintedDocumentationSentToTauronAsDone();
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
                .consentsVerification(consentsVerification != null ? consentsVerification.dto() : null)
                .documentCompilation(documentCompilation != null ? documentCompilation.dto() : null)
                .tauronCommunication(tauronCommunication != null ? tauronCommunication.dto() : null)
                .termVerification(termVerification != null ? termVerification.dto() : null)
                .build();
    }
}
