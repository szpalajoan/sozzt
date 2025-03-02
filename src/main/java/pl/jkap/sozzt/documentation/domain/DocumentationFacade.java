package pl.jkap.sozzt.documentation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.documentation.dto.AddDocumentationDto;
import pl.jkap.sozzt.documentation.dto.DocumentationDto;
import pl.jkap.sozzt.documentation.event.DocumentationCompletedEvent;
import pl.jkap.sozzt.documentation.exception.DocumentationNotFoundException;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;

import java.util.UUID;

@Slf4j
@Builder
public class DocumentationFacade {
    DocumentationRepository documentationRepository;
    DocumentationEventPublisher documentationEventPublisher;
    FileStorageFacade fileStorageFacade;

    public DocumentationDto getDocumentation(UUID uuid) {
        return documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid))
                .dto();
    }

    public void addDocumentation(AddDocumentationDto addDocumentationDto) {
        Documentation documentation = Documentation.builder()
                .documentationId(addDocumentationDto.getContractId())
                .deadline(addDocumentationDto.getDeadline())
                .consentsVerification(ConsentsVerification.notStartedConsentsVerification())
                .build();
        documentationRepository.save(documentation);
        log.info("Documentation added: {}", addDocumentationDto);
    }

    public void completeConsentsVerification(UUID uuid) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.completeConsentsVerification();
        documentationRepository.save(documentation);
        log.info("Consents verification completed: {}", uuid);
    }

    public FileDto uploadCompiledDocument(UUID uuid, AddFileDto addFileDto) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        FileDto fileDto = fileStorageFacade.addCompiledDocument(addFileDto);
        documentation.completeDocumentCompilation(fileDto.getFileId());
        documentationRepository.save(documentation);
        log.info("Compiled document uploaded: {}", uuid);
        return fileDto;
    }

    public void markPrintedDocumentationSentToTauronAsDone(UUID uuid) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.markPrintedDocumentationSentToTauronAsDone();
        documentationRepository.save(documentation);
        log.info("Printed documentation sent to Tauron: {}", uuid);
    }

    public void addTauronApprove(UUID uuid) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.addTauronApprove();
        documentationRepository.save(documentation);
        log.info("Tauron approve added: {}", uuid);
    }

    public void choosePersonResponsibleForTermVerification(UUID uuid, String verifierName) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.startTermVerification(verifierName);
        documentationRepository.save(documentation);
        log.info("Person responsible for term verification chosen: {}", uuid);
    }

    public void approveThatAllTermsAreActual(UUID uuid) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.approveThatAllTermsAreActual();
        documentationRepository.save(documentation);
        log.info("All terms are actual: {}", uuid);
    }

    public void completeDocumentation(UUID uuid) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.complete();
        documentationRepository.save(documentation);
        documentationEventPublisher.documentationCompleted(new DocumentationCompletedEvent(uuid));
        log.info("Documentation completed: {}", uuid);
    }
}
