package pl.jkap.sozzt.landextracts.infrastructure;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.landextracts.domain.LandExtractsFacade;
import pl.jkap.sozzt.landextracts.dto.LandExtractsDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/land-extracts/")
@SuppressWarnings("unused")
public class LandExtractsController {

    private final LandExtractsFacade landExtractsFacade;

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public LandExtractsDto getLandExtracts(@PathVariable UUID idContract) {
        return landExtractsFacade.getLandExtracts(idContract);
    }

    @PostMapping("{idContract}/request-for-land-extracts-sent")
    @ResponseStatus(HttpStatus.OK)
    public void requestForLandExtractsSent(@PathVariable UUID idContract) {
        landExtractsFacade.requestForLandExtractsSent(idContract);
    }

    @PostMapping("{idContract}/complete")
    @ResponseStatus(HttpStatus.OK)
    public void completeLandExtracts(@PathVariable UUID idContract) {
        landExtractsFacade.completeLandExtracts(idContract);
    }
} 