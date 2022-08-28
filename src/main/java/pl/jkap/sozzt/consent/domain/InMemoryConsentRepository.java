package pl.jkap.sozzt.consent.domain;


import pl.jkap.sozzt.utils.InMemoryRepository;

import java.util.UUID;

public class InMemoryConsentRepository extends InMemoryRepository<Consent, UUID> implements ConsentRepository {
    @Override
    public UUID getId(Consent entity) {
        return entity.getId();
    }
}
