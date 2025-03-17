package pl.jkap.sozzt.projectpurposesmappreparation.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.filestorage.dto.FileDto;
import pl.jkap.sozzt.projectpurposesmappreparation.domain.ProjectPurposesMapPreparationFacade;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/project-purposes-map-preparation/")
@SuppressWarnings("unused")
public class ProjectPurposesMapPreparationController {

    private final ProjectPurposesMapPreparationFacade projectPurposesMapPreparationFacade;

    @GetMapping("{contractId}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectPurposesMapPreparationDto getProjectPurposesMapPreparation(@PathVariable UUID contractId) {
        return projectPurposesMapPreparationFacade.getProjectPurposesMapPreparation(contractId);
    }

    @PostMapping("{contractId}/geodetic-maps")
    public ResponseEntity<FileDto> addGeodeticMap(@PathVariable UUID contractId, @RequestParam("file") MultipartFile file) {
        AddFileDto addPreliminaryUpdatedMapDto = AddFileDto.builder().file(file).contractId(contractId).build();
        FileDto addedFile = projectPurposesMapPreparationFacade.addGeodeticMap(addPreliminaryUpdatedMapDto);
        return ResponseEntity.ok(addedFile);
    }

    @PutMapping("{contractId}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeProjectPurposesMapPreparation(@PathVariable UUID contractId) {
        projectPurposesMapPreparationFacade.completeProjectPurposesMapPreparation(contractId);
    }
}
