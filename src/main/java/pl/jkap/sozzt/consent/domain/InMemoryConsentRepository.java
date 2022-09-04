package pl.jkap.sozzt.consent.domain;


import pl.jkap.sozzt.utils.InMemoryRepository;

import java.util.List;
import java.util.UUID;

class InMemoryConsentRepository extends InMemoryRepository<Consent, UUID> implements ConsentRepository {
    @Override
    public UUID getId(Consent entity) {
        return entity.getId();
    }

    @Override
    public List<Consent> findByIdContract(UUID idContract) {
        return null;
    }
}
