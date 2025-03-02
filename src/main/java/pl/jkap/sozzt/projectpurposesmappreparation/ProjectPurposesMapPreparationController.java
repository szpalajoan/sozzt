package pl.jkap.sozzt.projectpurposesmappreparation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade;

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


    @PostMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeProjectPurposesMapPreparation(@PathVariable UUID idContract) {
        projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(idContract);
    }
}
