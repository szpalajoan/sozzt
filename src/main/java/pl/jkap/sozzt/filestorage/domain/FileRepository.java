package pl.jkap.sozzt.filestorage.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.UUID;

interface FileRepository extends CrudRepository<File, UUID> {


    boolean existsById(@NotNull UUID fileId);

    List<File> findByObjectIdAndFileType(UUID objectId, FileType fileType);
}
