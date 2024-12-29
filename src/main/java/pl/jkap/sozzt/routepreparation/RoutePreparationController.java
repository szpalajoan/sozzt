package pl.jkap.sozzt.routepreparation;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.routepreparation.domain.RoutePreparationFacade;
import pl.jkap.sozzt.routepreparation.dto.RoutePreparationDto;

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


    @PostMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeRoutePreparation(@PathVariable UUID idContract) {
        routePreparationFacade.completeRoutePreparation(idContract);
    }
}
