package pl.jkap.sozzt.projectpurposesmappreparation.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.routedrawing.dto.PersonResponsibleForRouteDrawingDto;
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

    @PutMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeProjectPurposesMapPreparation(@PathVariable UUID idContract) {
        projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(idContract);
    }
}
