package pl.jkap.sozzt.projectpurposesmappreparation.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.PersonResponsibleForRouteDrawingDto;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/project-purposes-map-preparation/")
@SuppressWarnings("unused")
public class ProjectPurposesMapPreparationController {

    private final ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade;

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectPurposesMapPreparationDto getProjectPurposesMapPreparation(@PathVariable UUID idContract) {
        return projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(idContract);
    }

    @PostMapping("{idContract}/approve-correctness-of-the-map")
    @ResponseStatus(HttpStatus.CREATED)
    public void approveCorrectnessOfTheMap(@PathVariable UUID idContract) {
        projectPurposesMapPreparationFacade.approveCorrectnessOfTheMap(idContract);
    }

    @PostMapping("{idContract}/choose-person-responsible-for-route-drawing")
    @ResponseStatus(HttpStatus.CREATED)
    public void choosePersonResponsibleForRouteDrawing(@PathVariable UUID idContract, @RequestBody PersonResponsibleForRouteDrawingDto personResponsibleForRouteDrawingDto) {
        projectPurposesMapPreparationFacade.choosePersonResponsibleForRouteDrawing(idContract, personResponsibleForRouteDrawingDto.getUser());
    }

    @PostMapping("{idContract}/drawn-route")
    @ResponseStatus(HttpStatus.OK)
    public void addDrawnRoute(@PathVariable UUID idContract, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).build();
        projectPurposesMapPreparationFacade.uploadDrawnRoute(idContract, addFileDto);
    }

    @PostMapping("{idContract}/pdf-with-route-and-data")
    @ResponseStatus(HttpStatus.OK)
    public void addPdfWithRouteAndData(@PathVariable UUID idContract, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).build();
        projectPurposesMapPreparationFacade.uploadPdfWithRouteAndData(idContract, addFileDto);
    }

    @PutMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeProjectPurposesMapPreparation(@PathVariable UUID idContract) {
        projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(idContract);
    }
}
