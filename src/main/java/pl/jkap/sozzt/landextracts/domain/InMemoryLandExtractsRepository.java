package pl.jkap.sozzt.landextracts.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

class InMemoryLandExtractsRepository extends InMemoryRepository<LandExtracts, UUID> implements LandExtractsRepository {
    @Override
    protected UUID getIdFromEntity(LandExtracts entity) {
        return entity.getId();
    }
} 