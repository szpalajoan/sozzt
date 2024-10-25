package pl.jkap.sozzt.preliminaryplanning;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade;
import pl.jkap.sozzt.preliminaryplanning.dto.EditPreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/contracts/preliminary-plans/")
@SuppressWarnings("unused")
public class PreliminaryPlanController {

    private final PreliminaryPlanFacade preliminaryPlanFacade;

    @GetMapping("{idContract}")
    @ResponseStatus(HttpStatus.OK)
    public PreliminaryPlanDto getPreliminaryPlan(@PathVariable UUID idContract) {
        return preliminaryPlanFacade.getPreliminaryPlan(idContract);
    }

    @PutMapping("{idContract}")
    @ResponseStatus(HttpStatus.CREATED)
    public void editPreliminaryPlan(@PathVariable UUID idContract, @RequestBody EditPreliminaryPlanDto editPreliminaryPlanDto) {
        preliminaryPlanFacade.addGoogleMapUrl(idContract, editPreliminaryPlanDto.getGoogleMapUrl());
    }
}
