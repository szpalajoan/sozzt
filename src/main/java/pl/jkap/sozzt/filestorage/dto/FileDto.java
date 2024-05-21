package pl.jkap.sozzt.filestorage.dto;

import lombok.*;

import java.util.UUID;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FileDto {
    public enum FileTypeDto {
        CONTRACT_SCAN_FROM_TAURON,
        PRELIMINARY_MAP
    }
    private UUID fileId;
    private String fileName;
    private UUID objectId;
    private FileTypeDto fileType;
}
