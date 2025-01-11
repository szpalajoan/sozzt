package pl.jkap.sozzt.terrainvision.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.terrainvision.domain.TerrainVisionFacade;
import pl.jkap.sozzt.terrainvision.dto.RoutePreparationNeedDto;
import pl.jkap.sozzt.terrainvision.dto.TerrainVisionDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/terrain-vision/")
@SuppressWarnings("unused")
public class TerrainVisionController {

    private final TerrainVisionFacade terrainVisionFacade;

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public TerrainVisionDto getTerrainVision(@PathVariable UUID idContract) {
        return terrainVisionFacade.getTerrainVision(idContract);
    }

    @PostMapping("{idContract}/confirm-all-photos-are-uploaded")
    @ResponseStatus(HttpStatus.OK)
    public void confirmAllPhotosAreUploaded(@PathVariable UUID idContract) {
        terrainVisionFacade.confirmAllPhotosAreUploaded(idContract);
    }

    @PostMapping("{idContract}/route-preparation-need")
    @ResponseStatus(HttpStatus.OK)
    public void setRoutePreparationNeed(@PathVariable UUID idContract, @RequestBody RoutePreparationNeedDto routePreparationNeedDto) {
        terrainVisionFacade.setRoutePreparationNeed(idContract, routePreparationNeedDto.getRoutePreparationNeed());
    }

    @PostMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeTerrainVision(@PathVariable UUID idContract) {
        terrainVisionFacade.completeTerrainVision(idContract);
    }

}
