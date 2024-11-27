package pl.jkap.sozzt.filestorage.dto;

import lombok.*;
import pl.jkap.sozzt.filestorage.domain.FileType;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FileDto {
    private UUID fileId;
    private String fileName;
    private UUID objectId;
    private FileType fileType;
}
