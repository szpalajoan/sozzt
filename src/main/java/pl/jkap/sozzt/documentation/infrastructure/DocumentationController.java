package pl.jkap.sozzt.documentation.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.documentation.domain.DocumentationFacade;
import pl.jkap.sozzt.documentation.dto.DocumentationDto;
import pl.jkap.sozzt.documentation.dto.PersonResponsibleForRouteDrawingDto;
import pl.jkap.sozzt.documentation.dto.PersonResponsibleForTermVerificationDto;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/documentation/")
@SuppressWarnings("unused")
public class DocumentationController {

    private final DocumentationFacade documentationFacade;

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public DocumentationDto getDocumentation(@PathVariable UUID idContract) {
        return documentationFacade.getDocumentation(idContract);
    }

    @PostMapping("{idContract}/approve-correctness-of-the-map")
    @ResponseStatus(HttpStatus.CREATED)
    public void approveCorrectnessOfTheMap(@PathVariable UUID idContract) {
        documentationFacade.approveCorrectnessOfTheMap(idContract);
    }

    @PostMapping("{idContract}/choose-person-responsible-for-route-drawing")
    @ResponseStatus(HttpStatus.CREATED)
    public void choosePersonResponsibleForRouteDrawing(@PathVariable UUID idContract, @RequestBody PersonResponsibleForRouteDrawingDto personResponsibleForRouteDrawingDto) {
        documentationFacade.choosePersonResponsibleForRouteDrawing(idContract, personResponsibleForRouteDrawingDto.getUser());
    }

    @PostMapping("{idContract}/drawn-route")
    @ResponseStatus(HttpStatus.OK)
    public void addDrawnRoute(@PathVariable UUID idContract, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).build();
        documentationFacade.uploadDrawnRoute(idContract, addFileDto);
    }

    @PostMapping("{idContract}/pdf-with-route-and-data")
    @ResponseStatus(HttpStatus.OK)
    public void addPdfWithRouteAndData(@PathVariable UUID idContract, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).build();
        documentationFacade.uploadPdfWithRouteAndData(idContract, addFileDto);
    }

    @PutMapping("{idContract}/complete-consents-verification")
    @ResponseStatus(HttpStatus.OK)
    public void completeConsentsVerification(@PathVariable UUID idContract) {
        documentationFacade.completeConsentsVerification(idContract);
    }

    @PutMapping("{idContract}/complete-document-compilation")
    @ResponseStatus(HttpStatus.OK)
    public void uploadCompiledDocument(@PathVariable UUID idContract,@RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).build();
        documentationFacade.uploadCompiledDocument(idContract, addFileDto);
    }

    @PutMapping("{idContract}/mark-printed-documentation-sent-to-tauron-as-done")
    @ResponseStatus(HttpStatus.OK)
    public void markPrintedDocumentationSentToTauronAsDone(@PathVariable UUID idContract) {
        documentationFacade.markPrintedDocumentationSentToTauronAsDone(idContract);
    }

    @PutMapping("{idContract}/add-tauron-approve")
    @ResponseStatus(HttpStatus.OK)
    public void addTauronApprove(@PathVariable UUID idContract) {
        documentationFacade.addTauronApprove(idContract);
    }

    @PutMapping("{idContract}/choose-person-responsible-for-term-verification")
    @ResponseStatus(HttpStatus.OK)
    public void choosePersonResponsibleForTermVerification(@PathVariable UUID idContract, @RequestBody PersonResponsibleForTermVerificationDto person) {
        documentationFacade.choosePersonResponsibleForTermVerification(idContract, person.getUser());
    }

    @PutMapping("{idContract}/approve-that-all-terms-are-actual")
    @ResponseStatus(HttpStatus.OK)
    public void approveThatAllTermsAreActual(@PathVariable UUID idContract) {
        documentationFacade.approveThatAllTermsAreActual(idContract);
    }

    @PutMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeDocumentation(@PathVariable UUID idContract) {
        documentationFacade.completeDocumentation(idContract);
    }
}
