package pl.jkap.sozzt.preliminaryplanning.domain;

import org.springframework.data.repository.CrudRepository;

import java.util.UUID;


public interface PreliminaryPlanRepository extends CrudRepository<PreliminaryPlan, UUID> {
}
