package pl.jkap.sozzt.filestorage.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.Builder;
import lombok.Getter;
import pl.jkap.sozzt.filestorage.dto.FileDto;

import java.io.Serializable;
import java.util.UUID;

@Getter
@Builder
@Entity
class File implements Serializable {
    @Id
    private final UUID fileId;
    private final String fileName;
    private final UUID objectId;
    private final FileType fileType;
    private final String path;

    File(UUID fileId, String fileName, UUID objectId, FileType fileType, String path) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.objectId = objectId;
        this.fileType = fileType;
        this.path = path;
    }

    public FileDto dto() {
        return FileDto.builder()
                .fileId(fileId)
                .fileName(fileName)
                .fileType(FileDto.FileTypeDto.valueOf(fileType.name()))
                .objectId(objectId)
                .build();
    }
}
