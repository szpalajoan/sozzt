package pl.jkap.sozzt.filestorage.domain;

import pl.jkap.sozzt.inmemory.InMemoryRepository;

import java.util.List;
import java.util.UUID;

class InMemoryFileRepository extends InMemoryRepository<File, UUID> implements FileRepository {

    @Override
    protected <S extends File> UUID getIdFromEntity(S entity) {
        return entity.getFileId();
    }


    @Override
    public List<File> findByObjectIdAndFileType(UUID objectId, FileType fileType) {
        return table.values().stream().filter(file -> file.getContractId().equals(objectId) && file.getFileType().equals(fileType)).toList();
    }
}
