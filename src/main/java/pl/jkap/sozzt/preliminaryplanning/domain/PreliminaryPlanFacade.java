package pl.jkap.sozzt.preliminaryplanning.domain;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import pl.jkap.sozzt.filestorage.event.PreliminaryMapUploadedEvent;
import pl.jkap.sozzt.preliminaryplanning.dto.AddPreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.exception.PreliminaryPlanNotFoundException;

import java.util.UUID;

@Slf4j
@Builder
public class PreliminaryPlanFacade {
    private PreliminaryPlanRepository preliminaryPlanRepository;

    public void addPreliminaryPlan(AddPreliminaryPlanDto addPreliminaryPlanDto) {
        PreliminaryPlan preliminaryPlan = new PreliminaryPlan(addPreliminaryPlanDto.getPreliminaryPlanId(), addPreliminaryPlanDto.getDeadline());
        log.info("Preliminary planning added: {}", preliminaryPlan);
        preliminaryPlanRepository.save(preliminaryPlan);
    }

    PreliminaryPlanDto getPreliminaryPlan(UUID preliminaryPlanId) {
        return preliminaryPlanRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId))
                .dto();
    }

    private void preliminaryPlanUploaded(UUID preliminaryPlanId) {
        PreliminaryPlan preliminaryPlan = preliminaryPlanRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId));
        preliminaryPlan.confirmMapUploaded();
        preliminaryPlanRepository.save(preliminaryPlan);
    }

    @EventListener
    public void onPreliminaryMapUploadedEvent(PreliminaryMapUploadedEvent event) {
        preliminaryPlanUploaded(event.getPreliminaryPlanId());
    }


}
