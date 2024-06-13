package pl.jkap.sozzt.filestorage.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.UUID;

class InMemoryFileRepository extends InMemoryRepository<File, UUID> implements FileRepository {

    @Override
    protected <S extends File> UUID getIdFromEntity(S entity) {
        return entity.getFileId();
    }

    @Override
    public boolean existsByObjectIdAndFileType(UUID objectId, FileType fileType) {
        return table.values().stream().anyMatch(file -> file.getObjectId().equals(objectId) && file.getFileType().equals(fileType));
    }
}
