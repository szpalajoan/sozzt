package pl.jkap.sozzt.preliminaryplanning.domain;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import pl.jkap.sozzt.preliminaryplanning.dto.AddPreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.exception.PreliminaryPlanNotFoundException;

import java.util.UUID;

@Slf4j
@Builder
public class PreliminaryPlanFacade {
    private PreliminaryPlanRepository preliminaryPlanningRepository;

    public PreliminaryPlanDto addPreliminaryPlanning(AddPreliminaryPlanDto addPreliminaryPlanDto) {
        PreliminaryPlan preliminaryPlan = new PreliminaryPlan(addPreliminaryPlanDto.getPreliminaryPlanId(), addPreliminaryPlanDto.getDeadline());
        log.info("Preliminary planning added: {}", preliminaryPlan);
        return preliminaryPlanningRepository.save(preliminaryPlan).dto();
    }

    PreliminaryPlanDto getPreliminaryPlan(UUID preliminaryPlanId) {
        return preliminaryPlanningRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId))
                .dto();
    }
}
