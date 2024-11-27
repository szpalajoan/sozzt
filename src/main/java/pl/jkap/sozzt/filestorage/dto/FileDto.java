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
        PRELIMINARY_MAP,
        PHOTO_FROM_PLACE_OF_THE_CONTRACT,
        PRELIMINARY_MAP_UPDATED
    }
    private UUID fileId;
    private String fileName;
    private UUID objectId;
    private FileTypeDto fileType;
}
