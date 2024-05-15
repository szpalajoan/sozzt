package pl.jkap.sozzt.preliminaryplanning.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

public class InMemoryPreliminaryPlanRepository extends InMemoryRepository<PreliminaryPlan, UUID> implements PreliminaryPlanRepository {
    @Override
    protected <S extends PreliminaryPlan> UUID getIdFromEntity(S entity) {
        return entity.getPreliminaryPlanId();
    }
}
