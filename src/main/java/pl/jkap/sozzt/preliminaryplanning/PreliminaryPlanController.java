package pl.jkap.sozzt.preliminaryplanning;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import pl.jkap.sozzt.contract.domain.ContractFacade;
import pl.jkap.sozzt.contract.dto.ContractDto;
import pl.jkap.sozzt.contract.dto.CreateContractDto;
import pl.jkap.sozzt.contract.dto.EditContractDto;
import pl.jkap.sozzt.preliminaryplanning.domain.PreliminaryPlanFacade;
import pl.jkap.sozzt.preliminaryplanning.dto.EditPreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;

import java.util.List;
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

    @PutMapping("{idContract}/google-map-url")
    @ResponseStatus(HttpStatus.CREATED)
    public void addGoogleMapUrl(@PathVariable UUID idContract, @RequestBody EditPreliminaryPlanDto editPreliminaryPlanDto) {
         preliminaryPlanFacade.addGoogleMapUrl(idContract, editPreliminaryPlanDto.getGoogleMapUrl());
    }
}
