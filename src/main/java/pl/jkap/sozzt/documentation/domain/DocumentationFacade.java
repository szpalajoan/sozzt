package pl.jkap.sozzt.documentation.domain;

import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.documentation.dto.AddDocumentationDto;
import pl.jkap.sozzt.documentation.dto.DocumentationDto;
import pl.jkap.sozzt.documentation.event.DocumentationCompletedEvent;
import pl.jkap.sozzt.documentation.exception.DocumentationNotFoundException;
import pl.jkap.sozzt.documentation.exception.InvalidPersonResponsibleForRouteDrawingException;
import pl.jkap.sozzt.filestorage.domain.FileStorageFacade;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.instant.InstantProvider;

import java.util.UUID;

@Slf4j
@Builder
public class DocumentationFacade {
    DocumentationRepository documentationRepository;
    DocumentationEventPublisher documentationEventPublisher;
    FileStorageFacade fileStorageFacade;
    InstantProvider instantProvider;

    DocumentationDto getDocumentation(UUID uuid) {
        return documentationRepository.findById(uuid).orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid)).dto();
    }

    public void addDocumentation(AddDocumentationDto addDocumentationDto) {
        Documentation documentation = Documentation.builder()
                .documentationId(addDocumentationDto.getContractId())
                .deadline(addDocumentationDto.getDeadline())
                .mapVerification(new MapVerification())
                .build();
        documentationRepository.save(documentation);
        log.info("Documentation added: {}", addDocumentationDto);
    }

    public void approveCorrectnessOfTheMap(UUID uuid) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.approveCorrectnessOfTheMap(instantProvider);
        documentationRepository.save(documentation);
        log.info("Correctness of the map approved: {}", uuid);
    }

    public void choosePersonResponsibleForRouteDrawing(UUID uuid, String user) {
        if(user == null) {
            throw new InvalidPersonResponsibleForRouteDrawingException("Person responsible for route drawing cannot be null.");
        }
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        documentation.startRouteDrawing(user);
        documentationRepository.save(documentation);
        log.info("Person responsible for route drawing chosen: {}", uuid);
    }


    public FileDto uploadDrawnRoute(UUID documentationId, AddFileDto addFileDto) {
        Documentation documentation = documentationRepository.findById(documentationId)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + documentationId));
        FileDto fileDto = fileStorageFacade.addMapWithRoute(addFileDto);
        documentation.addDrawnRoute(fileDto.getFileId());
        documentationRepository.save(documentation);
        log.info("Drawn route uploaded: {}", documentationId);
        return fileDto;
    }

    public FileDto uploadPdfWithRouteAndData(UUID uuid, AddFileDto addFileDto) {
        Documentation documentation = documentationRepository.findById(uuid)
                .orElseThrow(() -> new DocumentationNotFoundException("Documentation not found: " + uuid));
        FileDto fileDto = fileStorageFacade.addPdfWithRouteAndData(addFileDto);
        documentation.addPdfWithRouteAndData(fileDto.getFileId());
        documentationRepository.save(documentation);
        log.info("Pdf with route and data uploaded: {}", uuid);
        return fileDto;
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
