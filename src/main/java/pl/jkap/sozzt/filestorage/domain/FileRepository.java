package pl.jkap.sozzt.filestorage.domain;

import org.jetbrains.annotations.NotNull;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.Repository;

import java.util.UUID;

interface FileRepository extends CrudRepository<File, UUID> {


    boolean existsById(@NotNull UUID fileId);
}
