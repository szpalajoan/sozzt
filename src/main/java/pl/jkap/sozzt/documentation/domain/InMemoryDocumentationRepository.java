package pl.jkap.sozzt.documentation.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

public class InMemoryDocumentationRepository extends InMemoryRepository<Documentation, UUID> implements DocumentationRepository {
    @Override
    protected UUID getIdFromEntity(Documentation entity) {
        return entity.getDocumentationId();
    }
}
