package pl.jkap.sozzt.documentation.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.documentation.domain.DocumentationFacade;
import pl.jkap.sozzt.documentation.dto.DocumentationDto;
import pl.jkap.sozzt.documentation.dto.PersonResponsibleForRouteDrawingDto;
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
}
