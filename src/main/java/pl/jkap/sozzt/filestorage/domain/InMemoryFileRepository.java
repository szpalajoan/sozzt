package pl.jkap.sozzt.filestorage.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

class InMemoryFileRepository extends InMemoryRepository<File, UUID> implements FileRepository {

    @Override
    protected <S extends File> UUID getIdFromEntity(S entity) {
        return entity.getFileId();
    }
}
