package pl.jkap.sozzt.projectpurposesmappreparation.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;


public class InMemoryProjectPurposesMapPreparationRepository extends InMemoryRepository<ProjectPurposesMapPreparation, UUID> implements ProjectPurposesMapPreparationRepository {
    @Override
    protected <S extends ProjectPurposesMapPreparation> UUID getIdFromEntity(S entity) {
        return entity.getProjectPurposesMapPreparationId();
    }

}
