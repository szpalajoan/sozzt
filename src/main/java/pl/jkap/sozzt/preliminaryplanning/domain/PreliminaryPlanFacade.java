package pl.jkap.sozzt.preliminaryplanning.domain;


import lombok.Builder;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.core.context.SecurityContextHolder;
import pl.jkap.sozzt.filestorage.event.PreliminaryMapDeletedEvent;
import pl.jkap.sozzt.filestorage.event.PreliminaryMapUploadedEvent;
import pl.jkap.sozzt.preliminaryplanning.dto.AddPreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.dto.PreliminaryPlanDto;
import pl.jkap.sozzt.preliminaryplanning.event.PreliminaryPlanCompletedEvent;
import pl.jkap.sozzt.preliminaryplanning.exception.PreliminaryPlanAccessException;
import pl.jkap.sozzt.preliminaryplanning.exception.CompletePreliminaryPlanException;
import pl.jkap.sozzt.preliminaryplanning.exception.PreliminaryPlanNotFoundException;

import java.util.UUID;

@Slf4j
@Builder
public class PreliminaryPlanFacade {
    private PreliminaryPlanRepository preliminaryPlanRepository;
    private PreliminaryPlanEventPublisher preliminaryPlanEventPublisher;

    public void addPreliminaryPlan(AddPreliminaryPlanDto addPreliminaryPlanDto) {
        PreliminaryPlan preliminaryPlan = new PreliminaryPlan(addPreliminaryPlanDto.getPreliminaryPlanId(), addPreliminaryPlanDto.getDeadline());
        log.info("Preliminary planning added: {}", preliminaryPlan);
        preliminaryPlanRepository.save(preliminaryPlan);
    }

    public void addGoogleMapUrl(UUID preliminaryPlanId, String googleMapUrl) {
        checkHasAccessToModifyPreliminaryPlan();
        PreliminaryPlan preliminaryPlan = preliminaryPlanRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId));
        preliminaryPlan.addGoogleMapUrl(googleMapUrl);
        preliminaryPlanRepository.save(preliminaryPlan);
    }

    public void completePreliminaryPlan(UUID preliminaryPlanId) {
        PreliminaryPlan preliminaryPlan = preliminaryPlanRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId));
        if(!preliminaryPlan.isCompleted()) {
            throw new CompletePreliminaryPlanException("Preliminary plan is not completed yet: " + preliminaryPlanId);
        }
        preliminaryPlanEventPublisher.preliminaryPlanCompleted(new PreliminaryPlanCompletedEvent(preliminaryPlanId));
    }

    public PreliminaryPlanDto getPreliminaryPlan(UUID preliminaryPlanId) {
        return preliminaryPlanRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId))
                .dto();
    }

    private void checkHasAccessToModifyPreliminaryPlan() {
        if(SecurityContextHolder.getContext().getAuthentication().getAuthorities().stream()
                .noneMatch(role -> role.getAuthority().equals("ROLE_PRELIMINARY_PLANER"))) {
            throw new PreliminaryPlanAccessException("Modification of preliminary plan is not allowed");
        }
    }

    private void preliminaryMapAdded(UUID preliminaryPlanId) {
        PreliminaryPlan preliminaryPlan = preliminaryPlanRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId));
        preliminaryPlan.confirmMapAdded();
        preliminaryPlanRepository.save(preliminaryPlan);
    }

    private void preliminaryPlanMapDeleted(UUID preliminaryPlanId) {
        PreliminaryPlan preliminaryPlan = preliminaryPlanRepository.findById(preliminaryPlanId)
                .orElseThrow(() -> new PreliminaryPlanNotFoundException("Preliminary planning not found: " + preliminaryPlanId));
        preliminaryPlan.confirmMapDeleted();
        preliminaryPlanRepository.save(preliminaryPlan);
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onPreliminaryMapUploadedEvent(PreliminaryMapUploadedEvent event) {
        preliminaryMapAdded(event.getPreliminaryPlanId());
    }

    @EventListener
    @SuppressWarnings("unused")
    public void onPreliminaryMapDeletedEvent(PreliminaryMapDeletedEvent event) {
        preliminaryPlanMapDeleted(event.getPreliminaryPlanId());
    }

}
