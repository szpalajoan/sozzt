package pl.jkap.sozzt.filestorage.domain;

import lombok.Getter;
import pl.jkap.sozzt.filestorage.dto.FileDto;

import java.util.UUID;

@Getter
class File {

    private final UUID fileId;
    private final String fileName;
    private final FileType fileType;
    private final String path;

    File(UUID fileId, String fileName, FileType fileType, String path) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileType = fileType;
        this.path = path;
    }

    public FileDto dto() {
        return FileDto.builder()
                .fileId(fileId)
                .fileName(fileName)
                .build();
    }
}
