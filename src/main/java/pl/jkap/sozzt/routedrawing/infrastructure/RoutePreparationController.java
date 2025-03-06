package pl.jkap.sozzt.routedrawing.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import pl.jkap.sozzt.filestorage.dto.AddFileDto;
import pl.jkap.sozzt.projectpurposesmappreparation.dto.ProjectPurposesMapPreparationDto;
import pl.jkap.sozzt.routedrawing.domain.RoutePreparationFacade;
import pl.jkap.sozzt.routedrawing.dto.PersonResponsibleForRouteDrawingDto;
import pl.jkap.sozzt.routedrawing.dto.RoutePreparationDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/route-preparation/")
@SuppressWarnings("unused")
public class RoutePreparationController {

    private final RoutePreparationFacade routePreparationFacade;

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public RoutePreparationDto getRoutePreparation(@PathVariable UUID idContract) {
        return routePreparationFacade.getRoutePreparation(idContract);
    }

    @PostMapping("{idContract}/approve-correctness-of-the-map")
    @ResponseStatus(HttpStatus.CREATED)
    public void approveCorrectnessOfTheMap(@PathVariable UUID idContract) {
        routePreparationFacade.approveCorrectnessOfTheMap(idContract);
    }

    @PostMapping("{idContract}/choose-person-responsible-for-route-drawing")
    @ResponseStatus(HttpStatus.CREATED)
    public void choosePersonResponsibleForRouteDrawing(@PathVariable UUID idContract, @RequestBody PersonResponsibleForRouteDrawingDto personResponsibleForRouteDrawingDto) {
        routePreparationFacade.choosePersonResponsibleForRouteDrawing(idContract, personResponsibleForRouteDrawingDto.getUser());
    }

    @PostMapping("{idContract}/drawn-route")
    @ResponseStatus(HttpStatus.OK)
    public void addDrawnRoute(@PathVariable UUID idContract, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).build();
        routePreparationFacade.uploadDrawnRoute(idContract, addFileDto);
    }

    @PostMapping("{idContract}/pdf-with-route-and-data")
    @ResponseStatus(HttpStatus.OK)
    public void addPdfWithRouteAndData(@PathVariable UUID idContract, @RequestParam("file") MultipartFile file) {
        AddFileDto addFileDto = AddFileDto.builder().file(file).contractId(idContract).build();
        routePreparationFacade.uploadPdfWithRouteAndData(idContract, addFileDto);
    }

    @PutMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeProjectPurposesMapPreparation(@PathVariable UUID idContract) {
        routePreparationFacade.completeRoutePreparation(idContract);
    }
}
