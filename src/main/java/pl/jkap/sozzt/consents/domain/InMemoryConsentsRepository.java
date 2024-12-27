package pl.jkap.sozzt.consents.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

public class InMemoryConsentsRepository extends InMemoryRepository<Consents, UUID> implements ConsentsRepository {
    @Override
    protected <S extends Consents> UUID getIdFromEntity(S entity) {
        return entity.getConsentId();
    }
}
